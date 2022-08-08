package com.vr.miniautorizador.handler;

public class GlobalError {
    
    private String message;
    private String error;

    public GlobalError(String message, String error) {
        this.message = message;
        this.error = error;
    }
    
    public GlobalError() {
    
    }
    
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((error == null) ? 0 : error.hashCode());
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GlobalError other = (GlobalError) obj;
        if (error == null) {
            if (other.error != null)
                return false;
        } else if (!error.equals(other.error))
            return false;
        if (message == null) {
            if (other.message != null)
                return false;
        } else if (!message.equals(other.message))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "GlobalError [error=" + error + ", message=" + message + "]";
    }
}
