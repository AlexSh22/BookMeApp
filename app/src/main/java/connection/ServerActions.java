package connection;

import android.app.Activity;

/**
 * Created by Alex-ssd on 23/09/2016.
 */
public class ServerActions {
    /* Message Constants */
    /* URL key */
    public static final String URL = "http://bookmeapp-144312.appspot.com";

    /* Actions defines */
    public static final String ACTION_NEW_USER_REGISTER = "new_user_registry";
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

}
