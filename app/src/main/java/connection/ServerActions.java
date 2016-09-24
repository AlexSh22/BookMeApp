package connection;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Alex-ssd on 23/09/2016.
 */
public class ServerActions {
    /* Message Constants */
    /* URL key */
    public static final String URL = "http://bookmeapp-144312.appspot.com";

    /* Actions defines */
    public static final String ACTION_COMMAND = "action";
    public static final String ACTION_NEW_USER_REGISTER = "new_user_registry";
    public static final String ACTION_ADD_BUSINESS = "add_business";
    public static final String ACTION_SEARCH_BUSINESS = "search_business";
    public static final String ACTION_REQUEST_APPOINTMENT = "request_appointment";
    public static final String SERVER_RET_VAL = "return value";
    public static final String SERVER_MSG = "msg";
    public static final String SERVER_DATA = "data";
    public static final String SERVER_DATA2 = "data2";
    public static final String SERVER_ACTION = "action";


    /* Variables */
    private Activity activity;
    private String br_action_name;

    //Constructor
    public ServerActions(Activity activity,final String br_action_name)
    {
        this.activity = activity;
        if (br_action_name != null)
            this.br_action_name = br_action_name;

        else
            this.br_action_name = "default";
    }

    public void new_user_registery(final String username, final String password, final Boolean is_worker)
    {
        JSONObject info = new JSONObject();
        /** Add data to JSON object */
        try
        {
            info.put(ACTION_COMMAND, ACTION_NEW_USER_REGISTER);
            info.put("username", username);
            info.put("password", password);
            if (is_worker)
                info.put("worker", "true");
            else
                info.put("worker", "false");
            System.out.println("try to send to server!!");
        }
        catch (JSONException e)
        {
            Log.e("JSONObject","IOException:" +  e.toString());
        }
        finally
        {
            /** call intentService */
            Intent msgIntent = new Intent(this.activity, HTTPIntentService.class);
            msgIntent.putExtra(HTTPIntentService.PARAM_URL, URL);
            msgIntent.putExtra(HTTPIntentService.PARAM_IN_MSG, info.toString());
            msgIntent.putExtra(HTTPIntentService.PARAM_BR_ACTION_NAME, br_action_name);
            System.out.println("finally send to server");

            this.activity.startService(msgIntent);
            System.out.println("start http activity");
        }
    }

    public void add_business(final String business_name, final String user_name, final String accept_time)
    {
        JSONObject info = new JSONObject();
        /** Add data to JSON object */
        try
        {
            info.put(ACTION_COMMAND, ACTION_ADD_BUSINESS);
            info.put("business_name", business_name);
            info.put("username", user_name);
            info.put("accept_time", accept_time);

            System.out.println("try to send to server!!");
        }
        catch (JSONException e)
        {
            Log.e("JSONObject","IOException:" +  e.toString());
        }
        finally
        {
            /** call intentService */
            Intent msgIntent = new Intent(this.activity, HTTPIntentService.class);
            msgIntent.putExtra(HTTPIntentService.PARAM_URL, URL);
            msgIntent.putExtra(HTTPIntentService.PARAM_IN_MSG, info.toString());
            msgIntent.putExtra(HTTPIntentService.PARAM_BR_ACTION_NAME, br_action_name);
            System.out.println("finally send to server");

            this.activity.startService(msgIntent);
            System.out.println("start http activity");
        }
    }

    public void search_business(final String business_name)
    {
        JSONObject info = new JSONObject();
        /** Add data to JSON object */
        try
        {
            info.put(ACTION_COMMAND, ACTION_SEARCH_BUSINESS);
            info.put("business_name", business_name);

            System.out.println("try to send to server!!");
        }
        catch (JSONException e)
        {
            Log.e("JSONObject","IOException:" +  e.toString());
        }
        finally
        {
            /** call intentService */
            Intent msgIntent = new Intent(this.activity, HTTPIntentService.class);
            msgIntent.putExtra(HTTPIntentService.PARAM_URL, URL);
            msgIntent.putExtra(HTTPIntentService.PARAM_IN_MSG, info.toString());
            msgIntent.putExtra(HTTPIntentService.PARAM_BR_ACTION_NAME, br_action_name);
            System.out.println("finally send to server");

            this.activity.startService(msgIntent);
            System.out.println("start http activity");
        }
    }

    public void request_appointment(final String business_name, final String appointment)
    {
        JSONObject info = new JSONObject();
        /** Add data to JSON object */
        try
        {
            info.put(ACTION_COMMAND, ACTION_REQUEST_APPOINTMENT);
            info.put("business_name", business_name);
            info.put("accept_time", appointment);

            System.out.println("try to send to server!!");
        }
        catch (JSONException e)
        {
            Log.e("JSONObject","IOException:" +  e.toString());
        }
        finally
        {
            /** call intentService */
            Intent msgIntent = new Intent(this.activity, HTTPIntentService.class);
            msgIntent.putExtra(HTTPIntentService.PARAM_URL, URL);
            msgIntent.putExtra(HTTPIntentService.PARAM_IN_MSG, info.toString());
            msgIntent.putExtra(HTTPIntentService.PARAM_BR_ACTION_NAME, br_action_name);
            System.out.println("finally send to server");

            this.activity.startService(msgIntent);
            System.out.println("start http activity");
        }
    }

}
