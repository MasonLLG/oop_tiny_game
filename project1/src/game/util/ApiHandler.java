package game.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * Utility class for sending prompts to the OpenAI ChatGPT API and handling responses.
 */
public class ApiHandler {

    // OpenAI API endpoint
    private static final String ENDPOINT = "https://api.openai.com/v1/chat/completions";

    private static final String API_KEY = "PLACEHOLDER";


    /**
     * Sends a prompt to the API and prints the full or partial response depending on the field specified.
     * Characters are streamed and printed with a typewriter effect.
     *
     * @param prompt The prompt to send to the API
     * @param fieldToPrintOut If not null, only this field's content will be printed
     * @return The complete response from the API
     */
    public static StringBuilder streamAndPrintResponse(String prompt, String fieldToPrintOut) {
        StringBuilder fullResponse = new StringBuilder();
        StringBuilder buffer = new StringBuilder();

        final int WAIT = 0, IN_FIELD = 1, DONE = 2;
        AtomicInteger state = new AtomicInteger(fieldToPrintOut == null ? DONE : WAIT);
        AtomicInteger printedChars = new AtomicInteger(0);
        AtomicInteger fieldStartInBuffer = new AtomicInteger(0);

        try {
            ApiHandler.sendPromptStream(prompt, token -> {
                fullResponse.append(token);
                buffer.append(token);

                if (state.get() == DONE) {
                    if (fieldToPrintOut == null) {
                        for (char c : token.toCharArray()) {
                            System.out.print(c);
                            System.out.flush();
                            try {
                                Thread.sleep(20);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
                    return;
                }

                if (state.get() == WAIT) {
                    String searchKey = "\"" + fieldToPrintOut + "\"";
                    int keyIdx = buffer.indexOf(searchKey);
                    if (keyIdx != -1) {
                        int colon = buffer.indexOf(":", keyIdx + searchKey.length());
                        int openQ = buffer.indexOf("\"", colon + 1);
                        if (openQ != -1) {
                            fieldStartInBuffer.set(openQ + 1);
                            state.set(IN_FIELD);
                        }
                    }
                }

                if (state.get() == IN_FIELD) {
                    int start = fieldStartInBuffer.get();
                    int endQuote = findClosingQuote(buffer, start);
                    int end = (endQuote != -1) ? endQuote : buffer.length();

                    for (int i = start + printedChars.get(); i < end; i++) {
                        char c = buffer.charAt(i);
                        System.out.print(c);
                        System.out.flush();
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }

                    printedChars.set(end - start);
                    if (endQuote != -1) {
                        state.set(DONE);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fullResponse;
    }

    /**
     * Finds the first unescaped quote character in the buffer after the given index.
     *
     * @param buf The text buffer to search
     * @param startIdx The index to start searching from
     * @return The index of the closing quote, or -1 if not found
     */
    private static int findClosingQuote(StringBuilder buf, int startIdx) {
        for (int i = startIdx; i < buf.length(); i++) {
            if (buf.charAt(i) == '"' && buf.charAt(i - 1) != '\\') {
                return i;
            }
        }
        return -1;
    }

    /**
     * Sends a prompt to the OpenAI API using GPT-4 and streams each response token to a callback.
     *
     * @param prompt The user prompt
     * @param onTokenReceived Callback to handle each token as it's streamed
     * @throws Exception If a connection or read error occurs
     */
    public static void sendPromptStream(String prompt, Consumer<String> onTokenReceived) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(ENDPOINT).openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Authorization", "Bearer " + API_KEY);
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

        try (OutputStream os = conn.getOutputStream()) {
            os.write(buildRequestBody(prompt).getBytes(StandardCharsets.UTF_8));
        }

        int status = conn.getResponseCode();
        InputStreamReader isr;
        if (status >= 400) {
            isr = new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8);
            try (BufferedReader errReader = new BufferedReader(isr)) {
                StringBuilder err = new StringBuilder();
                String line;
                while ((line = errReader.readLine()) != null) {
                    err.append(line).append('\n');
                }
                throw new IOException("Error from OpenAI API (HTTP " + status + "):\n" + err);
            }
        } else {
            isr = new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8);
        }

        try (BufferedReader reader = new BufferedReader(isr)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith("data: ")) continue;
                String data = line.substring(6).trim();
                if ("[DONE]".equals(data)) break;

                String token = extractDeltaContent(data);
                if (token != null && !token.isEmpty()) {
                    onTokenReceived.accept(token);
                }
            }
        }
    }

    /**
     * Builds the request body for the API call with the given prompt.
     *
     * @param prompt The prompt text
     * @return JSON string of the request body
     */
    private static String buildRequestBody(String prompt) {
        return "{"
                + "\"model\":\"gpt-4o-mini-2024-07-18\","
                + "\"stream\":true,"
                + "\"messages\":["
                +   "{\"role\":\"user\",\"content\":\"" + escapeJson(prompt) + "\"}"
                + "]"
                + "}";
    }

    /**
     * Extracts the content from a JSON delta update string.
     *
     * @param json JSON string containing delta content
     * @return Extracted string, or null if not found
     */
    private static String extractDeltaContent(String json) {
        int idx = json.indexOf("\"content\":\"");
        if (idx == -1) return null;
        int start = idx + 11;
        StringBuilder sb = new StringBuilder();
        boolean escape = false;
        for (int i = start; i < json.length(); i++) {
            char c = json.charAt(i);
            if (escape) {
                switch (c) {
                    case 'n': sb.append('\n'); break;
                    case 't': sb.append('\t'); break;
                    case '"': sb.append('"'); break;
                    case '\\': sb.append('\\'); break;
                    default: sb.append(c);
                }
                escape = false;
            } else if (c == '\\') {
                escape = true;
            } else if (c == '"') {
                break;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * Escapes special characters in a JSON string.
     *
     * @param text The string to escape
     * @return The escaped string
     */
    private static String escapeJson(String text) {
        return text.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "");
    }

    /**
     * Container for parsed riddle results.
     */
    public static class Result {
        public String riddle;
        public List<String> possibleAnswers;
        public String answer;
    }

    /**
     * Parses a riddle JSON string to extract the riddle, possible answers, and the correct answer.
     *
     * @param json The JSON string returned by the API
     * @return Parsed result as a Result object
     */
    public static Result parseRiddleJson(String json) {
        Result result = new Result();
        result.possibleAnswers = new ArrayList<>();

        int riddleStart = json.indexOf("\"Riddle\"");
        if (riddleStart != -1) {
            int colon = json.indexOf(':', riddleStart);
            int quoteStart = json.indexOf('"', colon + 1);
            int quoteEnd = json.indexOf('"', quoteStart + 1);
            if (quoteStart != -1 && quoteEnd != -1) {
                result.riddle = json.substring(quoteStart + 1, quoteEnd);
            }
        }

        int arrStart = json.indexOf("\"PossibleAnswers\"");
        if (arrStart != -1) {
            int bracketStart = json.indexOf('[', arrStart);
            int bracketEnd = json.indexOf(']', bracketStart);
            if (bracketStart != -1 && bracketEnd != -1) {
                String arrayContents = json.substring(bracketStart + 1, bracketEnd);
                String[] entries = arrayContents.split(",");
                for (String entry : entries) {
                    result.possibleAnswers.add(stripQuotes(entry.trim()));
                }
            }
        }

        int ansStart = json.indexOf("\"Answer\"");
        if (ansStart != -1) {
            int colon = json.indexOf(':', ansStart);
            int quoteStart = json.indexOf('"', colon + 1);
            int quoteEnd = json.indexOf('"', quoteStart + 1);
            if (quoteStart != -1 && quoteEnd != -1) {
                result.answer = json.substring(quoteStart + 1, quoteEnd);
            }
        }

        return result;
    }

    /**
     * Removes surrounding quotes from a string if present.
     *
     * @param s The input string
     * @return Unquoted string
     */
    private static String stripQuotes(String s) {
        if (s.startsWith("\"") && s.endsWith("\"") && s.length() >= 2) {
            return s.substring(1, s.length() - 1);
        }
        return s;
    }
}
