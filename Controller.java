package project;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 /**
 * @author Aseel Batuq, Batuam01
 * @author Michael Nguyen Nguymh02
 * @author Nick Everett evernp01
 */

public class Controller {

        @FXML
        private CheckBox filledChk;

        @FXML
        private Slider redSlider;
        @FXML
        private Slider greenSlider;
        @FXML
        private Slider blueSlider;

        @FXML
        private Rectangle circleMode;
        @FXML
        private Rectangle lineMode;
        @FXML
        private Rectangle rectMode;
        @FXML
        private Rectangle textMode;

        @FXML
        private Line menuLine;
        @FXML
        private Rectangle menuRectangle;
        @FXML
        private Circle menuCircle;
        @FXML
        private Text menuText;

        @FXML private Pane pane;

        private Color selectedColor;
        private Node selectedNode;
        private String insertMode;

        private Double X;
        private Double Y;
        @FXML
        void initialize() {

                menuCircle.setFill(Color.DODGERBLUE);
                menuRectangle.setFill(Color.web("#7bc6f8"));
                redSlider.valueProperty().addListener((obs, oldV, newV) -> {
                        int r =(int)  redSlider.getValue();
                        int g =(int) greenSlider.getValue();
                        int b = (int) blueSlider.getValue();
                        selectedColor = Color.rgb(r, g, b );
                        menuCircle.setFill(selectedColor);
                        menuRectangle.setFill(selectedColor);
                        menuLine.setStroke(selectedColor);
                        menuText.setFill(selectedColor);


                });
                greenSlider.valueProperty().addListener((obs, oldV, newV) -> {
                        int r =(int)  redSlider.getValue();
                        int g =(int) greenSlider.getValue();
                        int b = (int) blueSlider.getValue();
                        selectedColor = Color.rgb(r, g, b);
                        menuCircle.setFill(selectedColor);
                        menuRectangle.setFill(selectedColor);
                        menuLine.setStroke(selectedColor);
                        menuText.setFill(selectedColor);

                });

                blueSlider.valueProperty().addListener((obs, oldV, newV) -> {
                        int r =(int)  redSlider.getValue();
                        int g =(int) greenSlider.getValue();
                        int b = (int) blueSlider.getValue();
                        selectedColor = Color.rgb(r, g, b);
                        menuCircle.setFill(selectedColor);
                        menuRectangle.setFill(selectedColor);
                        menuLine.setStroke(selectedColor);
                        menuText.setFill(selectedColor);

                });
                redSlider.adjustValue(.25);
                greenSlider.adjustValue(.25);
                blueSlider.adjustValue(.25);
        }

        //Set insertMode and select the preview shape
        @FXML
        void circleMode(MouseEvent event) {
                deselect();
                this.insertMode = "Cirlce";
                select((Node)event.getTarget());

        }

        @FXML
        void lineMode(MouseEvent event) {
                deselect();
                this.insertMode = "Line";
                select((Node)event.getTarget());
        }

        @FXML
        void rectMode(MouseEvent event) {
                deselect();
                this.insertMode = "Rectangle";
                select((Node)event.getTarget());
        }

        @FXML
        void textMode(MouseEvent event) {
                deselect();
                this.insertMode = "Text";
                select((Node)event.getTarget());
        }

        //Detects all key events
        @FXML
        void globalKeyEvents(KeyEvent e) 
        {
                if (e.getCode() == KeyCode.ESCAPE) 
                {
                        deselect();
                        insertMode = "";
                        selectedNode = null;

                }

                if (e.getCode() == KeyCode.BACK_SPACE) 
                {
                        if(insertMode.equals("Text")) {
                                String s = ((Text) selectedNode).getText();
                                if (s.length() > 0){
                                        s = s.substring(0, s.length() - 1);
                                        ((Text) selectedNode).setText(s);

                                } 
                               
                        }
                        else {
                            pane.getChildren().remove(selectedNode);
                            
                    }

                } else {
                        if (selectedNode instanceof Text){
                                String s = ((Text) selectedNode).getText() + e.getText();
                                ((Text) selectedNode).setText(s);
                        }
                        
                        else if (e.getCode() == KeyCode.SPACE) 
                        {
                                ((Text) selectedNode).setText(((Text) selectedNode).getText() + " " + "");
                        }
                        else if (e.getCode() == KeyCode.SHIFT) 
                        {
                                if(e.getCode() != KeyCode.SHIFT) 
                                {
                                        ((Text) selectedNode).setText(((Text) selectedNode).getText() + e.getCode() + "");
                                }
                        }



                        }
                }


        @FXML
        //Mouse press create the shape
        void createShape(MouseEvent e) 
        {
        if(insertMode.equals("Line")){
                Line line = new Line();
                line.setStartX(e.getX()); 
                line.setStartY(e.getY()); 
                line.setEndX(e.getX());
                line.setEndY(e.getY());

                select(line);

                pane.addEventHandler(MouseEvent.MOUSE_RELEASED, eve ->{
                        ((Line) selectedNode).setEndX(eve.getX() );
                        ((Line) selectedNode).setEndY(eve.getY() );

                });
                //COLOR OF LINE         
                line.setStroke(selectedColor);
                selectedNode.setOnMouseClicked(ev -> { select(line);
                });
                selectedNode.setOnMouseDragged(event -> {
                        moveShape(event.getX(), event.getY());
                });

                //ADDING LINE TO PANE
                pane.getChildren().add(line);
        }
        //RECTANGLE SHAPE
        else if(insertMode.equals("Rectangle")) {
                Rectangle rect = new Rectangle();
                rect.setWidth(30);
                rect.setHeight(30);
                rect.setX(e.getX());
                rect.setY(e.getY());
                select(rect);

                if(!(filledChk.isSelected())) {
                        rect.setFill(Color.TRANSPARENT);
                        rect.setStroke(selectedColor);
                }
                else {
                        rect.setFill(selectedColor);
                }
                selectedNode.setOnMouseClicked(event -> {
                        select(rect);
                });
                selectedNode.setOnMouseDragged(event -> {
                        moveShape(event.getY(), event.getX());
                });
                pane.getChildren().add(rect);
        }



        else if(insertMode.equals("Cirlce")) {
                Circle circ = new Circle();
                circ.setCenterX(e.getX());
                circ.setCenterY(e.getY());
                circ.setRadius(10);
                select(circ);

                if(!(filledChk.isSelected())) {

                        circ.setFill(Color.TRANSPARENT);
                        circ.setStroke(selectedColor);  
                }
                else {

                        circ.setFill(selectedColor);
                }

                selectedNode.setOnMouseClicked(event -> {
                        select(circ);
                });
                selectedNode.setOnMouseDragged(event -> {
                        moveShape(event.getX(), event.getY());
                });

                pane.getChildren().add(circ);
        }


        //TEXT SHAPE
        else if(insertMode.equals("Text")) {
                double[] start = {e.getX(), e.getY()};
                Text text = new Text(0, 0,  "Text");
                text.setX(e.getX());
                text.setY(e.getY());


                if(!(filledChk.isSelected())) {
                        text.setFill(Color.TRANSPARENT);
                        text.setStrokeWidth(1);
                        text.setStroke(selectedColor);

                }
                else {
                        text.setFill(selectedColor);
                }
//              pane.getChildren().add(text);
                select(text);
                selectedNode.setOnMouseClicked(event -> {
                        select(text);
                        insertMode = "text";
                });
                selectedNode.setOnMouseDragged(event -> {
                        moveShape(event.getX(), event.getY());
                });

                pane.getChildren().add(text);

        }

        //FILLED OR NOT
        if(!(filledChk.isSelected())) {
                //GUI Shape
                menuCircle.setFill(Color.TRANSPARENT);
                menuCircle.setStroke(selectedColor);
                menuRectangle.setFill(Color.TRANSPARENT);
                menuRectangle.setStroke(selectedColor);
                menuLine.setFill(Color.TRANSPARENT);
                menuLine.setStroke(selectedColor);
                menuText.setFill(Color.TRANSPARENT);
                menuText.setStrokeWidth(1);
                menuText.setStroke(selectedColor);
        }
        else {
                menuCircle.setFill(selectedColor);
                menuRectangle.setFill(selectedColor);
                menuLine.setStroke(selectedColor);
                menuText.setFill(selectedColor);
        }
}


        private void moveShape(double x, double y) {
               
                if (selectedNode instanceof Text){
                        ((Text) selectedNode).setX(x);
                        ((Text) selectedNode).setY(y);

                } else if (selectedNode instanceof Circle) {
                        ((Circle) selectedNode).setCenterX(x);
                        ((Circle) selectedNode).setCenterY(y);
                }               
         else if (selectedNode instanceof Rectangle) {
                 ((Rectangle)selectedNode).setX(y);
                 ((Rectangle)selectedNode).setY(x);
        }               
                else if (selectedNode instanceof Line) {
//                        ((Line)selectedNode).setStartX(x);
//                        ((Line)selectedNode).setStartY(y);
                }       
}

        @FXML
        //Mouse drag to size the shape that was just created
        void resizeShape(MouseEvent ev) 
        {
                if ("Text".equals(insertMode)){
                        double fontSize = (((Text) selectedNode).getX() - ((Text) selectedNode).getY()) + (ev.getX() - ev.getY());
                        ((Text) selectedNode).setFont(new Font(fontSize));
                }
                if(insertMode.equals("Cirlce")) {
                	((Circle) selectedNode).setRadius(ev.getX());
                }
                
                if(insertMode.equals("Rectangle")) {
                	((Rectangle) selectedNode).setWidth(ev.getX());
                	((Rectangle) selectedNode).setHeight(ev.getY());
                }
               

//                Node lastShape = pane.getChildren().get(pane.getChildren().size() - 1);
//                if(lastShape instanceof Line) {
//                        if(((Line)lastShape).getStartY() != ((Line) lastShape).getEndY()) { 
//                        ((Line) selectedNode).setEndX(ev.getX());
//                        ((Line) selectedNode).setEndY(ev.getY());
//                        }
//                }
//                if(lastShape instanceof Circle) {
//                        ((Circle) selectedNode).setRadius(ev.getX() );
//                        
//                }
//
//                        if(lastShape instanceof Rectangle) {
//                        ((Rectangle) selectedNode).setWidth(ev.getX());
//                        ((Rectangle) selectedNode).setHeight(ev.getY());
//                }
    }



        void colorChange() 
        {
                this.menuCircle.setFill(selectedColor);
                this.menuLine.setFill(selectedColor);
                this.menuRectangle.setFill(selectedColor);
                this.menuText.setFill(selectedColor);

        }


        void select(Node n) 
        {
                deselect();
                selectedNode = n;
                addBorder(n);
        }

        void addBorder(Node n) 
        {
                try 
                {
                        n.getStyleClass().add("selected");
                } 
                catch (Exception e) 
                {
                        //Ignore when no node is selected
                }
        }

        void deselect() 
        {
                removeBorder(selectedNode);
                selectedNode = null;
        }

        void removeBorder(Node n) 
        {
                try {
                        n.getStyleClass().remove("selected");
                } 
                catch (NullPointerException e) 
                {
                        //Ignore when no node is selected
                }
        }
}


