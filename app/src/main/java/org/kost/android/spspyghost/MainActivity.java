package org.kost.android.spspyghost;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    final String LOGTAG="SpyGhost";
    SpyGhost sg;
    // String GhostIP="192.168.8.247";
    String GhostIP="192.168.16.188";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        sg=new SpyGhost();
        sg.destserver=GhostIP;

    }

    public void doRightCircle (Integer thing) {
        try {
            SpyGhost sg1=new SpyGhost();
            sg1.destserver=GhostIP;

            Log.i(LOGTAG,"Conecting to: "+sg1.destserver+":"+sg1.destport);
            sg1.connect();
            if (sg1.connected) {
                Log.i(LOGTAG, "Connected");
            } else {
                Log.w(LOGTAG, "Failed connecting");
            }
            sg1.goRightCircle(30);
            sg1.close();
        } catch (IOException e) {
            // ignore
            Log.w(LOGTAG, "Exception happened");
        }
    }

    public void doConnect () {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            public void run() {
                if (!sg.connected) {
                    sg.connect();
                }
            }

        });
    }

    public void doDisconnect () {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            public void run() {
                if (sg.connected) {
                    sg.close();
                }
            }

        });
    }

    public void doSomething (View v) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            public void run() {
                if (sg.connected) {
                    try {
                        sg.goRightCircle(30);
                    } catch (IOException e) {
                        Log.w(LOGTAG,"Error: "+e.toString());
                    }
                }
            }

        });
    }

    public void doForward (View v) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            public void run() {
                if (sg.connected) {
                    try {
                        sg.goForward(5);
                    } catch (IOException e) {
                        Log.w(LOGTAG,"Error: "+e.toString());
                    }
                }
            }

        });
    }

    public void doBackwards (View v) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            public void run() {
                if (sg.connected) {
                    try {
                        sg.goBackward(5);
                    } catch (IOException e) {
                        Log.w(LOGTAG,"Error: "+e.toString());
                    }
                }
            }

        });
    }

    public void doUpRight (View v) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            public void run() {
                if (sg.connected) {
                    try {
                        sg.goRightCircle(5);
                    } catch (IOException e) {
                        Log.w(LOGTAG,"Error: "+e.toString());
                    }
                }
            }

        });
    }

    public void doUpLeft (View v) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            public void run() {
                if (sg.connected) {
                    try {
                        sg.goLeftCircle(5);
                    } catch (IOException e) {
                        Log.w(LOGTAG,"Error: "+e.toString());
                    }
                }
            }

        });
    }

    public void doLCircle (View v) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            public void run() {
                if (sg.connected) {
                    try {
                        sg.goRightCircle(30);
                    } catch (IOException e) {
                        Log.w(LOGTAG,"Error: "+e.toString());
                    }
                }
            }

        });
    }

    public void doRCircle (View v) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            public void run() {
                if (sg.connected) {
                    try {
                        sg.goLeftCircle(30);
                    } catch (IOException e) {
                        Log.w(LOGTAG,"Error: "+e.toString());
                    }
                }
            }

        });
    }


    public void doPyro (View v) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            public void run() {
                if (sg.connected) {
                    try {
                        for (int i=0; i<10; i++) {
                            sg.goRightCircle(10);
                            sg.goLeftCircle(10);
                        }
                    } catch (IOException e) {
                        Log.w(LOGTAG,"Error: "+e.toString());
                    }
                }
            }

        });
    }


    public class connectTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... param) {
            doRightCircle(param[0]);
            return null;
        }
    };


    public class funnyTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... param) {
            doRightCircle(param[0]);
            return null;
        }
    };

    public void onRCircleButtonClick (View v) {
        Log.i(LOGTAG, "Right button click");

        new funnyTask().execute(0);

        Log.i(LOGTAG, "Finished");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_connect) {
            doConnect();
            return true;
        }

        if (id == R.id.action_disconnect) {
            doDisconnect();
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(new Runnable() { public void run() {
                    doSomething(null);
            } });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
