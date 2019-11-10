package MyProject_JavaFX;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class FXMLDocumentController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    //Encryption Program
    @FXML
    private Button encryption;
    @FXML
    private Button decryption;
    @FXML
    private TextField simpleText;
    @FXML
    private TextField encryptedText;
    @FXML
    private Label showMessage;
    @FXML
    private Button reset;

    @FXML
    public void encryptionText(ActionEvent event){
        final Tooltip tooltipEncryption = new Tooltip();
        tooltipEncryption.setText("With this button you can encryption the text.");
        encryption.setTooltip(tooltipEncryption);
        
        StringBuilder pinakasstr=new StringBuilder(simpleText.getText());
        for (int i=0;i<pinakasstr.length();i++){
            pinakasstr.setCharAt(i, (char)(pinakasstr.charAt(i)+1));
        }
        encryptedText.setText(pinakasstr.toString());
        simpleText.setText("");
        showMessage.setText("Encrypted!");
    }
    
    @FXML
    public void decryptionText(ActionEvent event){
        final Tooltip tooltipDecryption = new Tooltip();
        tooltipDecryption.setText("With this button you can decryption the text.");
        decryption.setTooltip(tooltipDecryption);
        
        StringBuilder pinakasstr=new StringBuilder(encryptedText.getText());
        for (int i=0;i<pinakasstr.length();i++){
            pinakasstr.setCharAt(i, (char)(pinakasstr.charAt(i)-1));
        }
        simpleText.setText(pinakasstr.toString());
        encryptedText.setText("");
        showMessage.setText("Decrypted!");
    }
    
    @FXML
    public void resetText(ActionEvent event){
        final Tooltip tooltipReset = new Tooltip();
        tooltipReset.setText("With this button you can clear the labels.");
        reset.setTooltip(tooltipReset);
        
        simpleText.setText("");
        encryptedText.setText("");
        showMessage.setText("Start!");
    }
    
    //Simple Editor Program
    
    @FXML
    private Button open;
    @FXML
    private Button save;
    @FXML
    private Button copy;
    @FXML
    private Label showMessageFile;
    @FXML
    private TextArea textAreaFile;
    @FXML
    private Label numberOfCharacters;
    @FXML
    private Label numberOfWords;
    
    @FXML
    public void openFile(ActionEvent event) throws IOException{
        final Tooltip tooltipOpen = new Tooltip();
        tooltipOpen.setText("WIth this button you can open the file.");
        open.setTooltip(tooltipOpen);
        
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new ExtensionFilter("TXT Files", "*.txt"));
        fc.setTitle("Open File");
        File selectedFile = fc.showOpenDialog(null);
        FileReader fr = new FileReader(selectedFile.getAbsolutePath().toString());
        BufferedReader br = new BufferedReader(fr);
        StringBuilder sb = new StringBuilder();
        String myText="";
        while((myText=br.readLine())!=null){
            sb.append(myText+"\n");
        }
        showMessageFile.setText("The file is opened!");
        textAreaFile.setText(sb.toString());
        counts();
    }
    
    @FXML
    public void saveFile(ActionEvent event) throws IOException{
        final Tooltip tooltipSave = new Tooltip();
        tooltipSave.setText("With this button you can save the file.");
        save.setTooltip(tooltipSave);
        
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new ExtensionFilter("TXT Files", "*.txt"));
        fc.setTitle("Save File");
        File selectedFile = fc.showSaveDialog(null);
        FileWriter fw = new FileWriter(selectedFile.getAbsolutePath());
        fw.write(textAreaFile.getText().toString());
        showMessageFile.setText("The file was saved!");
        fw.close();   
    }
    
    private void counts(){
        String str=textAreaFile.getText();
        numberOfCharacters.setText(String.valueOf(str.length()));
        if (str.length()==0)
            numberOfWords.setText("0");
        else{
            String[] strsplit=str.split("\\W+");
            numberOfWords.setText(String.valueOf(strsplit.length));
        }
    }
    
    
    @FXML
    public void copyText(ActionEvent event){
        final Tooltip tooltipCopy = new Tooltip();
        tooltipCopy.setText("With this button you can copy selected text.");
        copy.setTooltip(tooltipCopy);
        
        String myText=textAreaFile.getSelectedText().toString();
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        
        content.putString(myText);
        if(myText.equals(""))
            showMessageFile.setText("You not chosen anything");
        else
            showMessageFile.setText("The selected text was copied!");
        clipboard.setContent(content);
    }
    
    @FXML
    private void typedCount(KeyEvent event){
        counts();
    }
    
    //Calculator
    public class Model {

    public long calculate(long number1, long number2, String operator) {
        switch (operator) {
            case "+":
                return number1 + number2;
            case "-":
                return number1 - number2;
            case "*":
                return number1 * number2;
            case "/":
                if (number2 == 0)
                    return 0;

                return number1 / number2;
        }

        System.out.println("Unknown operator - " + operator);
        return 0;
    }
    }
    @FXML
    private Text output;
    
    private long number1 = 0;
    private String operator = "";
    private boolean start = true;

    private Model model = new Model();
    
    @FXML
    public void clearCalculator(ActionEvent event){
        output.setText("");
    }
    
    @FXML
    public void processNumpad(ActionEvent event){
        if (start) {
            output.setText("");
            start = false;
        }

        String value = ((Button)event.getSource()).getText();
        output.setText(output.getText() + value);
    }
    
    @FXML
    public void processOperator(ActionEvent event){
        String value = ((Button)event.getSource()).getText();

        if (!"=".equals(value)) {
            if (!operator.isEmpty())
                return;

            operator = value;
            number1 = Long.parseLong(output.getText());
            output.setText("");
        }
        else {
            if (operator.isEmpty())
                return;

            output.setText(String.valueOf(model.calculate(number1, Long.parseLong(output.getText()), operator)));
            operator = "";
            start = true;
        }
    }
    
    //Settings
    
    @FXML
    private TabPane program;
    @FXML
    private RadioButton defaultColor;
    @FXML
    private RadioButton blackColor;
    
    @FXML
    public void changeColorDefault(ActionEvent event){
        program.setStyle("-fx-background-color: WHITE;");
    }
    
    @FXML
    public void changeColorBlack(ActionEvent event){  
        program.setStyle("-fx-background-color: #656566; -fx-color: DARKGREY;");
        
    }
       
}

