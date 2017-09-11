package uic.edu.translator;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.Locale;

/**
 * Created by nick hargreaves on 4/23/15.
 */


public class GTranslate extends AsyncTask<String, Void, String> {

    public static String GOOGLE_API_KEY = "AIzaSyCjT4cq4FyfvZBC-vHMhXw4QHKUjTUTOXk";

    public static String translatedText(String text, String inputLang, String outputLang) {

        if (inputLang.equals(outputLang))
            return text;

        text = URLEncoder.encode(text);

        String translatedText = "";

        String sOriginal = findLanguageCode(inputLang);

        String sTranslated = findLanguageCode(outputLang);

        String requestURL = "https://www.googleapis.com/language/translate/v2?key=" +
                GOOGLE_API_KEY + "&q=" + text + "&source=" + sOriginal + "&target=" + sTranslated;
        String[] urls = new String[1];
        urls[0] = requestURL;
        AsyncTask readJSON = new GTranslate().execute(urls);

        try {
            JSONObject jsonObject = new JSONObject(readJSON.get().toString()).getJSONObject("data");

            jsonObject = jsonObject.getJSONArray("translations").getJSONObject(0);

            translatedText = jsonObject.getString("translatedText");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Success");
        }

        return translatedText;
    }

    @Override
    protected String doInBackground(String[] params) {
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(params[0]);
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } else {
                Log.e("JSON error", "Failed to get JSON object");
            }

            JSONObject jsonObject = new JSONObject(builder.toString()).getJSONObject("data");

            jsonObject = jsonObject.getJSONArray("translations").getJSONObject(0);

            String translatedText = jsonObject.getString("translatedText");

            MainActivity.outputText = translatedText;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public static String findLanguageCode(String language) {
        String languageShort = "en";

        if (language.equals("AFRIKAANS")) {
            languageShort = "af";
        }
        if (language.equals("ALBANIAN")) {
            languageShort = "sq";
        }
        if (language.equals("ARABIC")) {
            languageShort = "ar";
        }
        if (language.equals("AZERBAIJANI")) {
            languageShort = "az";
        }
        if (language.equals("BASQUE")) {
            languageShort = "eu";
        }
        if (language.equals("BENGALI")) {
            languageShort = "bn";
        }
        if (language.equals("BELARUSIAN")) {
            languageShort = "be";
        }
        if (language.equals("BULGARIAN")) {
            languageShort = "bg";
        }
        if (language.equals("CATALAN")) {
            languageShort = "ca";
        }
        if (language.equals("CHINESE SIMPLIFIED")) {
            languageShort = "zh-CN";
        }
        if (language.equals("CHINESE TRADITIONAL")) {
            languageShort = "zh-TW";
        }
        if (language.equals("CROATIAN")) {
            languageShort = "hr";
        }
        if (language.equals("CZECH")) {
            languageShort = "cs";
        }
        if (language.equals("DANISH")) {
            languageShort = "da";
        }
        if (language.equals("DUTCH")) {
            languageShort = "nl";
        }
        if (language.equals("ENGLISH")) {
            languageShort = "en";
        }
        if (language.equals("ESPERANTO")) {
            languageShort = "eo";
        }
        if (language.equals("ESTONIAN")) {
            languageShort = "et";
        }
        if (language.equals("FILIPINO")) {
            languageShort = "tl";
        }
        if (language.equals("FINNISH")) {
            languageShort = "fi";
        }
        if (language.equals("FRENCH")) {
            languageShort = "fr";
        }
        if (language.equals("GALICIAN")) {
            languageShort = "gl";
        }
        if (language.equals("GEORGIAN")) {
            languageShort = "ka";
        }
        if (language.equals("GERMAN")) {
            languageShort = "de";
        }
        if (language.equals("GREEK")) {
            languageShort = "el";
        }
        if (language.equals("GUJARATI")) {
            languageShort = "gu";
        }
        if (language.equals("HAITIAN CREOLE")) {
            languageShort = "ht";
        }
        if (language.equals("HEBREW")) {
            languageShort = "iw";
        }
        if (language.equals("HINDI")) {
            languageShort = "hi";
        }
        if (language.equals("HUNGARIAN")) {
            languageShort = "hu";
        }
        if (language.equals("ICELANDIC")) {
            languageShort = "is";
        }
        if (language.equals("INDONESIAN")) {
            languageShort = "id";
        }
        if (language.equals("IRISH")) {
            languageShort = "ga";
        }
        if (language.equals("ITALIAN")) {
            languageShort = "it";
        }
        if (language.equals("JAPANESE")) {
            languageShort = "ja";
        }
        if (language.equals("KANNADA")) {
            languageShort = "kn";
        }
        if (language.equals("KOREAN")) {
            languageShort = "ko";
        }
        if (language.equals("LATIN")) {
            languageShort = "la";
        }
        if (language.equals("LATVIAN")) {
            languageShort = "lv";
        }
        if (language.equals("LITHUANIAN")) {
            languageShort = "lt";
        }
        if (language.equals("MACEDONIAN")) {
            languageShort = "mk";
        }
        if (language.equals("MALAY")) {
            languageShort = "ms";
        }
        if (language.equals("MALTESE")) {
            languageShort = "mt";
        }
        if (language.equals("NORWEGIAN")) {
            languageShort = "no";
        }
        if (language.equals("PERSIAN")) {
            languageShort = "fa";
        }
        if (language.equals("POLISH")) {
            languageShort = "pl";
        }
        if (language.equals("PORTUGUESE")) {
            languageShort = "pt";
        }
        if (language.equals("ROMANIAN")) {
            languageShort = "ro";
        }
        if (language.equals("RUSSIAN")) {
            languageShort = "ru";
        }
        if (language.equals("SERBIAN")) {
            languageShort = "sr";
        }
        if (language.equals("SLOVAK")) {
            languageShort = "sk";
        }
        if (language.equals("SLOVENIAN")) {
            languageShort = "sl";
        }
        if (language.equals("SPANISH")) {
            languageShort = "es";
        }
        if (language.equals("SWAHILI")) {
            languageShort = "sw";
        }
        if (language.equals("SWEDISH")) {
            languageShort = "sv";
        }
        if (language.equals("TAMIL")) {
            languageShort = "ta";
        }
        if (language.equals("TELUGU")) {
            languageShort = "te";
        }
        if (language.equals("THAI")) {
            languageShort = "th";
        }
        if (language.equals("TURKISH")) {
            languageShort = "tr";
        }
        if (language.equals("UKRAINIAN")) {
            languageShort = "uk";
        }
        if (language.equals("URDU")) {
            languageShort = "ur";
        }
        if (language.equals("VIETNAMESE")) {
            languageShort = "vi";
        }
        if (language.equals("WELSH")) {
            languageShort = "cy";
        }
        if (language.equals("YIDDISH")) {
            languageShort = "yi";
        }

        return languageShort;
    }

    public static Locale findLanguage(String language) {



        if (language.equals("CHINESE SIMPLIFIED")) {
            return Locale.SIMPLIFIED_CHINESE;
        }
        if (language.equals("CHINESE TRADITIONAL")) {
            return Locale.TRADITIONAL_CHINESE;
        }
        if (language.equals("ENGLISH")) {
            return Locale.ENGLISH;
        }

        if (language.equals("FRENCH")) {
            return Locale.FRENCH;
        }

        if (language.equals("GERMAN")) {
            return Locale.GERMAN;
        }

        if (language.equals("ITALIAN")) {
            return Locale.ITALIAN;
        }
        if (language.equals("JAPANESE")) {
            return Locale.JAPANESE;
        }


        if (language.equals("KOREAN")) {
            return Locale.KOREAN;
        }


        return Locale.ENGLISH;


    }
}