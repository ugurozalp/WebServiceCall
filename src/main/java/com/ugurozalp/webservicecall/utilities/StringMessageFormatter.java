package com.ugurozalp.webservicecall.utilities;

class StringMessageFormatter {

    private void replace(StringBuffer buff, String toReplace, String replaceTo) {
        int start;
        while ((start = buff.indexOf(toReplace)) >= 0)
            buff.replace(start, start + toReplace.length(), replaceTo);
    }

    public final String formatMessage(StringBuffer message) {
        replace(message, "&lt;\\?xml version=\"1.0\" encoding=\"utf-16\"\\?&gt;", "");
        replace(message, "&#xD;", "");
        replace(message, "&lt;", "<");
        replace(message, "&gt;", ">");
        replace(message, "\t", "");
        replace(message, "\n", "");
        replace(message, "&amp;lt;", "<");
        replace(message, "&amp;gt;", ">");
        replace(message, "'", "\"");
        replace(message, "  ", "");

        return message.toString();
    }
}
