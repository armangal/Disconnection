package com.pt.pok.client;

import java.math.BigDecimal;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Disconnection
    implements EntryPoint {

    /**
     * The message displayed to the user when the server cannot be reached or returns an error.
     */
    private static final String SERVER_ERROR = "An error occurred while " + "attempting to contact the server. Please check your network "
                                               + "connection and try again.";

    /**
     * Create a remote service proxy to talk to the server-side Greeting service.
     */
    private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {

        JSONObject jsonObj = JSONParser.parseStrict("{\"smallBlind\":1.01}").isObject();

        RootPanel panel = RootPanel.get();
        final FlexTable ft = new FlexTable();
        panel.add(ft);
        ft.setText(0, 0, "String");
        ft.setText(0, 1, "Formatted");
        int i = 1;
        ft.setText(i, 0, "0.01");
        ft.setText(i++, 1, format(new BigDecimal("0.01")));

        ft.setText(i, 0, "0.1");
        ft.setText(i++, 1, format(new BigDecimal("0.1")));

        ft.setText(i, 0, "0.10");
        ft.setText(i++, 1, format(new BigDecimal("0.10")));

        ft.setText(i, 0, "1.01");
        ft.setText(i++, 1, format(new BigDecimal("1.01")));

        ft.setText(i, 0, "1.11");
        ft.setText(i++, 1, format(new BigDecimal("1.11")));

        ft.setText(i, 0, "0.11");
        ft.setText(i++, 1, format(new BigDecimal("0.11")));

        ft.setText(i, 0, "0.0011");
        ft.setText(i++, 1, format(new BigDecimal("0.0011")));

        ft.setText(i, 0, "1.1111");
        ft.setText(i++, 1, format(new BigDecimal("1.1111")));

        ft.setText(i, 0, "1.1155");
        ft.setText(i++, 1, format(new BigDecimal("1.1155")));

        final TextBox text = new TextBox();
        text.setText("0.01");
        ft.setWidget(i, 0, text);

        final int row = i;
        ft.setText(i++, 1, format(new BigDecimal(text.getText())));

        text.addBlurHandler(new BlurHandler() {

            @Override
            public void onBlur(BlurEvent event) {
                String jsonString = "{\"smallBlind\":" + text.getText() + "}";
                Log.debug("jsonString: " + jsonString);
                ft.setText(row, 1, format(getBigDecimal(JSONParser.parseStrict(jsonString).isObject(), "smallBlind")));

            }
        });

        text.addKeyUpHandler(new KeyUpHandler() {

            @Override
            public void onKeyUp(KeyUpEvent event) {
                String jsonString = "{\"smallBlind\":" + text.getText() + "}";
                Log.debug("jsonString: " + jsonString);
                ft.setText(row, 1, format(getBigDecimal(JSONParser.parseStrict(jsonString).isObject(), "smallBlind")));

            }
        });

    }

    public static BigDecimal getBigDecimal(JSONValue jsonVal, String name) {
        JSONObject jsonObj = jsonVal.isObject();
        if (jsonObj == null) {
            return null;
        }
        Log.debug("jsonObj:" + jsonObj);
        String doubleValue = jsonObj.get(name).toString();
        Log.debug("doubleValue:" + doubleValue);
        return jsonObj.get(name) != null ? new BigDecimal(doubleValue) : null;
    }

    public String format(BigDecimal value) {
        if (value != null) {
            BigDecimal setScale = value.setScale(2, BigDecimal.ROUND_HALF_EVEN);
            Log.debug("After setScale:" + setScale);
            String string = setScale.toPlainString();
            return "$" + removeTrailingZeros(string);
        }
        return " ";
    }

    private String removeTrailingZeros(String string) {
        int endIndex = string.length();
        while (string.charAt( --endIndex) == '0') {}
        if (Character.isDigit(string.charAt(endIndex))) {
            endIndex++;
        }
        return string.substring(0, endIndex);
    }
}
