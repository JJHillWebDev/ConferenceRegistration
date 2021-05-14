import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
    This is a JavaFX application that calculates the registration fees
    for a conference. The general conference registration fee is $895
    per person and student registration is $495 per person. There is 
    also an optional opening night dinner with a keynote speech for $30
    per person. In addition, there are optional preconference workshops.

    The application allows the the user to select the registration type,
    the optional opening night dinner and keynote speech, and as many
    preconference workshops as desired and will display the total cost.

    @author Jeremy Hill
    @version 1.8.0_271
 */
public class ConferenceRegistration extends Application 
{
    /**
    * The main method calls the Application class launch
    * @param args the command line arguments
    */
    public static void main(String[] args) 
    {
         launch(args);
    }

    //Fields
    private RadioButton generalReg, studentReg;
    private ToggleGroup radioGroup;
    private CheckBox optionDinner;
    private Label totalCost;
    private Label optionWorkshop;
    private ListView<String> workshops;
    private Button calculatePrice;
    private final double[] PRICES = {295, 295, 395, 395};

    /**
    * The start method takes a Stage object as an argument.
    * It also creates a VBox which holds RadioButtons for registration
    * type, a CheckBox for the optional dinner, a ListView of optional 
    * workshops to attend and a Button that calculates the total price
    * of the conference based on the options that the user selects. 
    * @param primaryStage Stage object to display scene
    */
    @Override
    public void start(Stage primaryStage) 
    {
        //create RadioButton controls
        generalReg = new RadioButton("General Registration");
        studentReg = new RadioButton("Student Registration");
        //add RadioButton controls to a ToggleGroup
        radioGroup = new ToggleGroup();
        generalReg.setToggleGroup(radioGroup);
        studentReg.setToggleGroup(radioGroup);
        //select the generalReg button
        generalReg.setSelected(true);

        //create a VBox for RadioButtons
        VBox radioBox = new VBox(generalReg, studentReg);
        //set VBox position, padding and spacing
        radioBox.setSpacing(10);
        radioBox.setAlignment(Pos.CENTER_LEFT);

        //create CheckBox for dinner
        optionDinner = new CheckBox("Opening Night Dinner");
        //create a Label for the Workshop ListView
        optionWorkshop = new Label("Select an Option Workshop");
        //creating a ListView for optional workshop selection
        workshops = new ListView<>();
        workshops.getItems().addAll("Introduction to E-commerce", 
                                    "The Future of the Web", 
                                    "Advanced Java Programming", 
                                    "Network Security");
        //enable selection mode for multiple items
        workshops.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //create a VBox for the optional workshops
        VBox workshopBox = new VBox(optionWorkshop, workshops);
        //set VBox position, padding and spacing
        workshopBox.setSpacing(10);
        workshopBox.setAlignment(Pos.CENTER_LEFT);

        //create a Label to hold the total cost of the conference
        totalCost = new Label("Cost:   $0.00");
        //create a Button that calculates the total price
        calculatePrice = new Button("Calculate Cost");

        //register an event handler for the Button
        calculatePrice.setOnAction(event ->
        {
            //holds the total price for the conference
            double total = 0;

            //checks if the generalReg RadioButton is selected
            if(generalReg.isSelected())
            total += 895;
            //checks if the studentReg RadioButton is selected
            if(studentReg.isSelected())
            total += 495;
            //checks if the optionDinner CheckBox is selected
            if(optionDinner.isSelected())
            total += 30;

            //create an ObservableList of selected workshops
            ObservableList<Integer> selections = 
                workshops.getSelectionModel().getSelectedIndices();

            //check if any workshops were selected
            if (selections != null) 
            {
                //loop through the ObservableList
                for (int num : selections) 
                {
                    //add the cost of the selected workshops to total
                    total += PRICES[num];
                }
            }
            //display the results
            totalCost.setText(String.format("Cost:   $%,.2f", total));
        }); 
		//create a VBox for the entire scene
        VBox root = new VBox(radioBox, optionDinner, workshopBox, 
        					totalCost, calculatePrice);
        //set VBox position, padding and spacing
        root.setSpacing(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(15));

        //create a scene and display it 
        Scene scene = new Scene(root, 250, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}