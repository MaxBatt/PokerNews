package de.pokernews.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
 
/**
 * Checks if the device has access to the internet
 *
 * @author Max Batt	
 */
public class ConnectionCheck {
     
    private Context _context;
     
    public ConnectionCheck(Context context){
        this._context = context;
    }
 
    public boolean checkConnection(){
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
          if (connectivity != null)
          {
              NetworkInfo[] info = connectivity.getAllNetworkInfo();
              if (info != null)
                  for (int i = 0; i < info.length; i++)
                      if (info[i].getState() == NetworkInfo.State.CONNECTED)
                      {
                          return true;
                      }
 
          }
          return false;
    }
}
