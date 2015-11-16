package com.example.nidhin.multispeakerrecognition;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by nidhin on 11/11/15.
 */
public class Logger {


    File logFile = null;
    private static Logger _logger = null;
    public static Logger getLogger()
    {
        if(null == _logger)
            _logger = new Logger();
        return  _logger;

    }
    public Logger()
    {


        logFile =  new File(Environment.getExternalStorageDirectory() + "/mmm/ALCLog.txt");
        if(logFile.exists())
            logFile.delete();
            try
            {
                logFile.createNewFile();
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


    }

    public void appendLog(String text)
    {
        try
        {
            text =  new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()) +" ::"+text;
            //BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append(text);
            buf.newLine();
            buf.close();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
