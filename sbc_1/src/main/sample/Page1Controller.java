package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Objects;

public class Page1Controller {

    @FXML
    ImageView gravitateImage;

    @FXML
    TextField listaAccidenteFabricaTextField;

    @FXML
    TextArea listaAccidenteFabricaTextArea;

    @FXML
    TextField listaAngajatiFabricaTextField;

    @FXML
    TextArea listaAngajatiFabricaTextArea;

    @FXML
    TextArea gravitateAccidenteTextArea;

    @FXML
    ComboBox<String> gravitateAccidenteComboBox;

    ObservableList<String> gravitateComboBoxList = FXCollections.observableArrayList("usor","mediu", "grav");


    public void initialize() {
        gravitateAccidenteComboBox.setItems(gravitateComboBoxList);
    }

    @FXML
    void listaAccidenteFabricaTextFieldOnAction(ActionEvent event) {
        boolean existaFabrica=false;
        String temp= listaAccidenteFabricaTextField.getText();
        StringBuilder continut= new StringBuilder();


        try {
            File inputFile = new File("fabrici.XML");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();


            NodeList nList = doc.getElementsByTagName("fabrica"); // extrag toate fabricile intr-un obiect NodeList

            for (int i = 0; i < nList.getLength(); i++) {       // parcurg obiectul, adica fiecare fabrica
                Node nNode = nList.item(i);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element fabrica = (Element) nNode;          // daca obiectul obtinut e chiar nod, il transf in element

                    if(Objects.equals(fabrica.getElementsByTagName("nume").item(0).getTextContent(), temp) || Objects.equals(temp, "")) // daca numele fabricii corespunde sau daca nu am introdus nimic(se afiseaza toate acc)
                    {
                        existaFabrica=true;
                        NodeList accidentList = fabrica.getElementsByTagName("accident");  //salvez toate accidentele din fabrica
                        if (accidentList.getLength() > 0) {
                            // daca sunt accidente in fabrica afiseaza si numele fabricii
                            continut.append("Fabrica: ").append(fabrica.getElementsByTagName("nume").item(0).getTextContent()).append("\n");
                        }

                        for (int j = 0; j < accidentList.getLength(); j++) {          // parcurg toate accidentele pentru a accesa nr
                            Node accidentNode = accidentList.item(j);
                            if (accidentNode.getNodeType() == Node.ELEMENT_NODE) {

                                Element accident = (Element) accidentNode;
                                String s1 = "Numar accident: " + accident.getElementsByTagName("numar").item(0).getTextContent() + "\n";
                                String s2 = "Victime: " + accident.getElementsByTagName("victime").item(0).getTextContent() + "\n";
                                String s3 = "Valoare daune: " + accident.getElementsByTagName("daune").item(0).getTextContent() + " lei" + "\n";
                                continut.append(s1).append(s2).append(s3).append("Angajati implicati:\n");

                                NodeList angajatiList = fabrica.getElementsByTagName("angajat");  //salvez toti angajatii din fabrica
                                for (int k = 0; k < angajatiList.getLength(); k++) {          // parcurg toti angajatii pentru a vedea daca sunt implicati
                                    // in accidentul curent
                                    Node angajatNode = angajatiList.item(k);

                                    if (angajatNode.getNodeType() == Node.ELEMENT_NODE) {
                                        Element angajat = (Element) angajatNode;

                                        //daca numarul accidentului actual e egal cu nr accidentului in care a fost implicat angajatul
                                        if (Objects.equals(accident.getElementsByTagName("numar").item(0).getTextContent(), angajat.getElementsByTagName("nr_accident").item(0).getTextContent())) {
                                            String s11 = angajat.getElementsByTagName("nume").item(0).getTextContent() + " ";
                                            String s12 = angajat.getElementsByTagName("prenume").item(0).getTextContent() + " ";
                                            continut.append(s11).append(s12);
                                            if (!Objects.equals(angajat.getElementsByTagName("saptamani").item(0).getTextContent(), "")) {
                                                String s13 = angajat.getElementsByTagName("saptamani").item(0).getTextContent() + " saptamani refacere\n";
                                                continut.append(s13);
                                            } else continut.append("\n");
                                        }
                                    }
                                }

                            }
                            continut.append("\n");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(existaFabrica && continut.toString().equals("")){
            listaAccidenteFabricaTextArea.setText("Fabrica "+temp+" nu are inregistrate accidente");
        }else if(!existaFabrica){
            listaAccidenteFabricaTextArea.setText("Fabrica "+temp+" nu exista");
        }else
            listaAccidenteFabricaTextArea.setText(continut.toString());
    }

    @FXML
    void listaAngajatiFabricaTextFieldOnAction(ActionEvent event) {
        boolean existaFabrica=false;

        String temp= listaAngajatiFabricaTextField.getText();
        StringBuilder continut= new StringBuilder();


        try {
            File inputFile = new File("fabrici.XML");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("fabrica"); // extrag toate fabricile intr-un obiect NodeList

            for (int i = 0; i < nList.getLength(); i++) {       // parcurg obiectul, adica fiecare fabrica
                Node nNode = nList.item(i);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element fabrica = (Element) nNode;          // daca obiectul obtinut e chiar nod, il transf in element

                    if(Objects.equals(fabrica.getElementsByTagName("nume").item(0).getTextContent(), temp) || Objects.equals(temp, "")) // daca numele fabricii corespunde sau caseta e goala
                    {
                        existaFabrica = true;
                        NodeList angajatiList=fabrica.getElementsByTagName("angajat");  //salvez toti angajatii din fabrica
                        if (angajatiList.getLength() > 0) {
                            // daca sunt angajati in fabrica afiseaza si numele fabricii
                            continut.append("Fabrica: ").append(fabrica.getElementsByTagName("nume").item(0).getTextContent()).append("\n");
                        }
                        for(int j=0; j<angajatiList.getLength(); j++){          // parcurg toti angajatii pentru a le obtine numele
                            Node angajatNode = angajatiList.item(j);

                            if (angajatNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element angajat = (Element) angajatNode;

                                String s1 = angajat.getElementsByTagName("nume").item(0).getTextContent() + " ";
                                String s2 = angajat.getElementsByTagName("prenume").item(0).getTextContent() + " ";
                                String s4 = angajat.getElementsByTagName("salariu").item(0).getTextContent() + " lei"+"\n";
                                continut.append(s1).append(s2).append(s4);
                            }
                        }
                    }
                }
                continut.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(continut);
        if(existaFabrica){
            listaAngajatiFabricaTextArea.setText(continut.toString());
        }
        else{
            listaAngajatiFabricaTextArea.setText("Nu exista o fabrica numita "+temp);
        }
    }

    @FXML
    void gravitateAccidenteComboBoxOnAction(ActionEvent event) {
        String temp=gravitateAccidenteComboBox.getValue();

        String imagePath1 = "D:\\Facultate\\An 4\\Sem 1\\Sisteme bazate pe cunostinte (V)\\lab\\Tema 2 SBC\\Tema 2 SBC Saioc_Marius_Andrei\\sbc_1\\src\\main\\images\\usor.png";
        String imagePath2 = "D:\\Facultate\\An 4\\Sem 1\\Sisteme bazate pe cunostinte (V)\\lab\\Tema 2 SBC\\Tema 2 SBC Saioc_Marius_Andrei\\sbc_1\\src\\main\\images\\mediu.png";
        String imagePath3 = "D:\\Facultate\\An 4\\Sem 1\\Sisteme bazate pe cunostinte (V)\\lab\\Tema 2 SBC\\Tema 2 SBC Saioc_Marius_Andrei\\sbc_1\\src\\main\\images\\grav.png";

        Image poza1 = new Image(new File(imagePath1).toURI().toString());
        Image poza2 = new Image(new File(imagePath2).toURI().toString());
        Image poza3 = new Image(new File(imagePath3).toURI().toString());

        if (Objects.equals(temp, "usor"))
            gravitateImage.setImage(poza1);
        else if (Objects.equals(temp, "mediu"))
            gravitateImage.setImage(poza2);
        else
            gravitateImage.setImage(poza3);


        StringBuilder continut= new StringBuilder();

        try {
            File inputFile = new File("fabrici.XML");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();


            NodeList nList = doc.getElementsByTagName("accident"); // extrag toate accidentele intr-un obiect NodeList

            for (int i = 0; i < nList.getLength(); i++) {       // parcurg accidentele
                Node nNode = nList.item(i);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element accident = (Element) nNode;          // daca obiectul obtinut e chiar nod, il transf in element

                    NodeList regula1List = doc.getElementsByTagName("regula1");
                    for (int j = 0; j < regula1List.getLength(); j++) {      //trec prin corpul regulii1 pt a extrage variabilele si a face verificari
                        boolean setBrake=false;
                        Node regula1Node = regula1List.item(j);
                        if (regula1Node.getNodeType() == Node.ELEMENT_NODE) {
                            Element regula1 = (Element) regula1Node;
                            //verificam gravitatea accidentului in functie de daune
                            if(!Objects.equals(accident.getElementsByTagName("daune").item(0).getTextContent(), ""))
                                if (Integer.parseInt(accident.getElementsByTagName("daune").item(0).getTextContent()) >=
                                        Integer.parseInt(regula1.getElementsByTagName("r1daune").item(0).getTextContent())) {
                                    if (Objects.equals(regula1.getElementsByTagName("gravitate").item(0).getTextContent(), temp)) {
                                        String s1 = "Numar: " + accident.getElementsByTagName("numar").item(0).getTextContent() + "\n";
                                        String s2 = "Victime: " + accident.getElementsByTagName("victime").item(0).getTextContent() + "\n";
                                        String s3 = "Daune: " + accident.getElementsByTagName("daune").item(0).getTextContent() + " lei" + "\n";
                                        continut.append(s1).append(s2).append(s3).append("\n");
                                    }
                                    break;
                                }

                            //daca gravitatea nu a fost setata in functie de daune poate fi setata in functie de
                            //durata accidentarii unui angajat. Asa ca acum se va face trecea prin toti angajatii,
                            //iar la cei participanti in accidentul curent va fi comparata durata accidentarii
                            NodeList angajatiList =doc.getElementsByTagName("angajat");  //salvez toti angajatii
                            for (int k = 0; k < angajatiList.getLength(); k++) {          // parcurg toti angajatii pentru a vedea daca sunt implicati
                                // in accidentul curent
                                Node angajatNode = angajatiList.item(k);

                                if (angajatNode.getNodeType() == Node.ELEMENT_NODE) {
                                    Element angajat = (Element) angajatNode;
                                    //daca numarul accidentului actual e egal cu nr accidentului in care a fost implicat angajatul
                                    if (Objects.equals(accident.getElementsByTagName("numar").item(0).getTextContent(), angajat.getElementsByTagName("nr_accident").item(0).getTextContent())) {
                                        //compar durata accidentarii
                                        if (!Objects.equals(angajat.getElementsByTagName("saptamani").item(0).getTextContent(), ""))
                                            if (Integer.parseInt(angajat.getElementsByTagName("saptamani").item(0).getTextContent()) >=
                                                    Integer.parseInt(regula1.getElementsByTagName("r1saptamani").item(0).getTextContent())) {
                                                if (Objects.equals(regula1.getElementsByTagName("gravitate").item(0).getTextContent(), temp)) {
                                                    String s1 = "Numar: " + accident.getElementsByTagName("numar").item(0).getTextContent() + "\n";
                                                    String s2 = "Victime: " + accident.getElementsByTagName("victime").item(0).getTextContent() + "\n";
                                                    String s3 = "Daune: " + accident.getElementsByTagName("daune").item(0).getTextContent() + " lei" + "\n";
                                                    continut.append(s1).append(s2).append(s3).append("\n");
                                                }
                                                setBrake=true;
                                                break;
                                            }
                                    }
                                }
                            }
                            if(setBrake)
                                break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        gravitateAccidenteTextArea.setText(String.valueOf(continut));
    }
}