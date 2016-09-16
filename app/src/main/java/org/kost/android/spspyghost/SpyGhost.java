package org.kost.android.spspyghost;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

public class SpyGhost {
    int destport = 2001;
    String destserver = "192.168.16.188";
    Socket clientSocket = null;
    DataOutputStream outToServer = null;
    boolean connected=false;

    public byte[] pktcraft(int thrust, int direction) {
        byte[] tping = new byte[]{(byte) 0xa1, 0x7b, 0x00, 0x00, 0x00, 0x00, 0x00, 0x7b, (byte) 0xff};

        tping[2] = (byte) thrust;
        tping[3] = (byte) direction;
        tping[7] = chksum(tping);

        return tping;
    }

    public byte chksum(byte[] bytearray) {
        int sum = 0;

        for (int i = 1; i < 7; i++) {
            sum = sum + bytearray[i];
        }
        return (byte) (sum & 0xff);
    }

    public void connect()  {
        try {
            clientSocket = new Socket(destserver, destport);
            outToServer = new DataOutputStream(clientSocket.getOutputStream());
            connected=true;
        } catch (IOException e) {
            connected=false;
            Log.w("SpyGhost", "Error: " + e.toString());
        }
    }

    public void close() {
        try {
            clientSocket.close();
        } catch (IOException|NullPointerException e) {
            // ignore
        }
    }

    public byte[] pktsend(int thrust, int direction) throws IOException {
        byte[] rbuf = new byte[9];
        int tries = 0;
        if (!connected) return null;
        outToServer.write(pktcraft(thrust, direction));
        while (tries < 10) {
            if (clientSocket.getInputStream().available() != 0) {
                int howmuch = clientSocket.getInputStream().read(rbuf);
                break;
            } else {
                try {
                    Thread.sleep(100);
                    tries++;
                } catch (InterruptedException ie) {
                    // ignore
                } // try
            } // if-else
        } // while
        return rbuf;
    }

    public void goForward (int howmuch) throws IOException {
        for (int i=0; i<howmuch; i++) {
            pktsend(0x0f,0x00);
        }
    }

    public void goBackward (int howmuch) throws IOException {
        for (int i=0; i<howmuch; i++) {
            pktsend(0x8e,0x00);
        }
    }

    public void goRightCircle (int howmuch) throws IOException {
        for (int i=0; i<howmuch; i++) {
            pktsend(0x0f,0x1f);
        }
    }

    public void goLeftCircle (int howmuch) throws IOException {
        for (int i=0; i<howmuch; i++) {
            pktsend(0x0f,0x9f);
        }
    }
}
