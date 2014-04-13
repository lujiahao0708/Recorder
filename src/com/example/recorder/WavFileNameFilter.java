package com.example.recorder;

import java.io.File;
import java.io.FilenameFilter;

public class WavFileNameFilter implements FilenameFilter {  
  
    String type;  
  
    protected WavFileNameFilter() {  
    }  
  
    protected WavFileNameFilter(String type) {  
        super();  
        this.type = type;  
    }  
  
    @Override  
    public boolean accept(File dir, String filename) {  
        if (filename.endsWith(type)) {  
            return true;  
        }
        return false;  
    }  
      
    public void setType(String type) {  
    	this.type = type;
    }  
}  