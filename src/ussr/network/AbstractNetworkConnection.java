/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.network;
/**
 * 
 *   @author ups
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import ussr.model.ControllerImpl;
import ussr.samples.atron.ATRONController;


public abstract class AbstractNetworkConnection {
	private ServerSocket socket;
	private int port;
	boolean debug = true;
	public AbstractNetworkConnection(int port) {
	    this.port = port;
		try {
			socket = new ServerSocket(port);
		} catch (IOException exn) {
		    throw new Error("IO exception while creating network controller: "+exn);
		}
	}

	public int getPort() {
	    return port;
	}

	public void activate() {
        InputStream input;
        OutputStream output;
        Socket connection;
	    do {
	        try {
	            if(debug) System.out.println("Waiting for connection on port "+port);
	            connection = socket.accept();
	            if(debug) System.out.println("Received connection on port "+port);
	            input = connection.getInputStream();
	            output = connection.getOutputStream();
	        } catch (IOException exn) {
	            throw new Error("Unable to open connection");
	        }
	    }
	    while(activationHook(input,output,connection));
	}

	public abstract boolean activationHook(InputStream input, OutputStream output, Socket connection);
}
