

package com.stackroute.newintentparser.exude.api;

import com.stackroute.newintentparser.exude.common.ExudeRequest;
import com.stackroute.newintentparser.exude.common.ExudeResponse;
import com.stackroute.newintentparser.exude.exception.InvalidDataException;
import com.stackroute.newintentparser.exude.swear.SwearParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;

public class ExudeFileData implements ExudeAPI {

    Logger logger = Logger.getLogger("ExudeFileData");

    @Override
    public ExudeResponse filterStoppings(ExudeRequest exudeRequest) throws InvalidDataException {
        try {
            String fileData = getDataChunks(exudeRequest.getData());
            exudeRequest.setData(fileData);
            return ExudeAPIImpl.getInstance().filterStoppings(exudeRequest);
        } catch (IOException ex) {
            Logger.getLogger(ExudeFileData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidDataException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new InvalidDataException("Invalid Data");
        }
        throw new InvalidDataException("Invalid Data");
    }

    @Override
    public ExudeResponse filterStoppingKeepDuplicate(ExudeRequest exudeRequest) throws InvalidDataException {
        try {
            String fileData = getDataChunks(exudeRequest.getData());
            exudeRequest.setData(fileData);
            return ExudeAPIImpl.getInstance().filterStoppingWithDuplicate(exudeRequest);
        } catch (IOException ex) {
            Logger.getLogger(ExudeFileData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidDataException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        throw new InvalidDataException("Invalid Data");
    }

    @Override
    public ExudeResponse getSwearWords(ExudeRequest exudeRequest) throws InvalidDataException {
        StringBuilder finalFilteredData = new StringBuilder();
        try {
            String fileData = getDataChunks(exudeRequest.getData());
            SwearParser swearParser = SwearParser.getInstance();
            finalFilteredData.append(swearParser.getSwearWords(fileData));
            swearParser.resetSwearWords();
            ExudeResponse response = new ExudeResponse();
            response.setResultData(finalFilteredData.toString());
            return response;
        } catch (Exception e) {
           logger.log(Level.SEVERE, e.getMessage());
            throw new InvalidDataException("Invalid Data");
        }
    }

    private BodyContentHandler getFileData(String path) throws InvalidDataException {
        try {
            Parser parser = new AutoDetectParser();
            BodyContentHandler handler = new BodyContentHandler(10 * 1024 * 1024);
            Metadata metadata = new Metadata();
            FileInputStream inputstream = new FileInputStream(new File(path));
            ParseContext context = new ParseContext();
            parser.parse(inputstream, handler, metadata, context);
            return handler;
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new InvalidDataException(e.getMessage());
        }
    }

    private String getDataChunks(String path) throws IOException, InvalidDataException {
        BodyContentHandler contentHandler = getFileData(path);
        List<String> lines = new ArrayList<String>();
        StringBuilder stringBuilder = new StringBuilder();
        if (contentHandler != null) {
            String line = contentHandler.toString();
            String delims = " ";
            line = line.trim();
            StringTokenizer str = new StringTokenizer(line, delims);
            while (str.hasMoreElements()) {
                line = (String) str.nextElement();
                if (line.trim().length() > 0) {
                    lines.add(line);
                }
            }
            for (String unique : lines) {
                if (unique.trim().length() > 0) {
                    stringBuilder.append(unique + " ");
                }
            }
            return stringBuilder.toString();
        }
        throw new InvalidDataException("Invalid Data");
    }

}
