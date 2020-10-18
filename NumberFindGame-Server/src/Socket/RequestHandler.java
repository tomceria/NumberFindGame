package Socket;

public class RequestHandler {
    Thread requestHandleThread;
    SocketRequest requestRaw;

    public RequestHandler(SocketRequest requestRaw) {
        this.requestRaw = requestRaw;
        this.requestHandleThread = new Thread() {
            @Override
            public void run() {
                if (requestRaw.getMessage() != null) {
                    System.out.println(requestRaw.getMessage());
                }

                switch (requestRaw.getAction()) {
                    case LOGIN: {
                        SocketRequest_Login request = (SocketRequest_Login) requestRaw;
//                if (request.username.equals("luuminhhoang")) { System.out.println(String.format("%s logged in.", request.username));
//                    response = new SocketResponse(SocketResponse.Status.SUCCESS, "Successfully logged in!");
//                } else {
//                    response = new SocketResponse(SocketResponse.Status.FAILED, "Login failed.");
//                }
//                output.writeObject(response);
                        break;
                    }
                    case DISCONNECT: {
//                response = new SocketResponse(SocketResponse.Status.END);
//                output.writeObject(response);
//                closeSocket();
                        break;
                    }
                }
            }
        };
    }

    public void init() {
        requestHandleThread.start();
    }
}
