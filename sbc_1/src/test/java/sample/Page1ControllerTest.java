    package sample;

    import javafx.application.Platform;
    import javafx.event.ActionEvent;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.Parent;
    import javafx.scene.Scene;
    import javafx.scene.control.ComboBox;
    import javafx.scene.control.TextArea;
    import javafx.scene.control.TextField;
    import javafx.scene.image.ImageView;
    import javafx.stage.Stage;
    import org.junit.jupiter.api.*;
    import org.junit.jupiter.api.extension.ExtendWith;
    import org.testfx.framework.junit5.ApplicationTest;
    import org.testfx.framework.junit5.Start;

    import java.io.IOException;
    import java.nio.file.Files;
    import java.nio.file.Paths;
    import java.security.NoSuchAlgorithmException;
    import java.sql.SQLException;
    import java.util.concurrent.TimeUnit;

    import static org.junit.jupiter.api.Assertions.*;

    @Nested
    public class Page1ControllerTest extends ApplicationTest {
        private Page1Controller page1Controller;

        private static final String FABRICI_FILE_PATH = "fabrici.xml";
        private static final String FABRICI_BACKUP_FILE_PATH = "fabrici_backup.xml";

        private final String TEST_XML_DATA =
    """
    <?xml version="1.0" encoding="utf-8"?>
    <tema2>
        <fabrica>
            <nume>Arctic</nume>
            <oras>Gaesti</oras>
            <angajat>
                <prenume>Alex</prenume>
                <nume>Oprea</nume>
                <sex>m</sex>
                <salariu>5000</salariu>
                <nr_accident></nr_accident>
                <saptamani></saptamani>
            </angajat>
            <angajat>
                <prenume>Carina</prenume>
                <nume>Buti</nume>
                <sex>f</sex>
                <salariu>4000</salariu>
                <nr_accident>5</nr_accident>
                <saptamani>1</saptamani>
            </angajat>
            <angajat>
                <prenume>Nicolae</prenume>
                <nume>Dinescu</nume>
                <sex>m</sex>
                <salariu>3800</salariu>
                <nr_accident>5</nr_accident>
                <saptamani>10</saptamani>
            </angajat>
            <angajat>
                <prenume>Melania</prenume>
                <nume>Tunaru</nume>
                <sex>f</sex>
                <salariu>7800</salariu>
                <nr_accident></nr_accident>
                <saptamani></saptamani>
            </angajat>
            <accident>
                <numar>5</numar>
                <victime>da</victime>
                <daune>10000</daune>
            </accident>
        </fabrica>
        
        
        <fabrica>
            <nume>Dacia</nume>
            <oras>Mioveni</oras>
            <angajat>
                <prenume>Ionut</prenume>
                <nume>Tabacu</nume>
                <sex>m</sex>
                <salariu>6600</salariu>
                <nr_accident>4</nr_accident>
                <saptamani>10</saptamani>
            </angajat>
            <angajat>
                <prenume>Gabriel</prenume>
                <nume>Gherban</nume>
                <sex>m</sex>
                <salariu>4200</salariu>
                <nr_accident></nr_accident>
                <saptamani></saptamani>
            </angajat>
            <angajat>
                <prenume>Florentina</prenume>
                <nume>Vasilica</nume>
                <sex>f</sex>
                <salariu>5280</salariu>
                <nr_accident></nr_accident>
                <saptamani></saptamani>
            </angajat>	
            <accident>
                <numar>4</numar>
                <victime>da</victime>
                <daune>9999</daune>
            </accident>
        </fabrica>
        
        
        <fabrica>
            <nume>Fratii Schiel</nume>
            <oras>Brasov</oras>
            <angajat>
                <prenume>Madalina</prenume>
                <nume>Calin</nume>
                <sex>f</sex>
                <salariu>5500</salariu>
                <nr_accident></nr_accident>
                <saptamani></saptamani>
            </angajat>
            <angajat>
                <prenume>Zoe</prenume>
                <nume>Dirjan</nume>
                <sex>f</sex>
                <salariu>4900</salariu>
                <nr_accident></nr_accident>
                <saptamani></saptamani>
            </angajat>
            <angajat>
                <prenume>Robert</prenume>
                <nume>Tudor</nume>
                <sex>m</sex>
                <salariu>5100</salariu>
                <nr_accident></nr_accident>
                <saptamani></saptamani>
            </angajat>
        </fabrica>
        
        
        <fabrica>
            <nume>Astra</nume>
            <oras>Arad</oras>
            <angajat>
                <prenume>Valeriu</prenume>
                <nume>Barbu</nume>
                <sex>m</sex>
                <salariu>5600</salariu>
                <nr_accident></nr_accident>
                <saptamani></saptamani>
            </angajat>
            <angajat>
                <prenume>Smaranda</prenume>
                <nume>Pandaru</nume>
                <sex>f</sex>
                <salariu>3800</salariu>
                <nr_accident>2</nr_accident>
                <saptamani>0</saptamani>
            </angajat>
            <angajat>
                <prenume>Florin</prenume>
                <nume>Dragomir</nume>
                <sex>m</sex>
                <salariu>4400</salariu>
                <nr_accident>2</nr_accident>
                <saptamani>3</saptamani>
            </angajat>	
            <accident>
                <numar>2</numar>
                <victime>da</victime>
                <daune>4999</daune>
            </accident>
        </fabrica>
        
        
        <fabrica>
            <nume>Mekatronic</nume>
            <oras>Oradea</oras>
            <angajat>
                <prenume>Alfred</prenume>
                <nume>Tabacu</nume>
                <sex>m</sex>
                <salariu>7000</salariu>
                <nr_accident></nr_accident>
                <saptamani></saptamani>
            </angajat>
            <angajat>
                <prenume>Narcisa</prenume>
                <nume>Slaboiu</nume>
                <sex>f</sex>
                <salariu>4100</salariu>
                <nr_accident>3</nr_accident>
                <saptamani>2</saptamani>
            </angajat>
            <angajat>
                <prenume>Valentin</prenume>
                <nume>Nitu</nume>
                <sex>m</sex>
                <salariu>4390</salariu>
                <nr_accident>1</nr_accident>
                <saptamani>0</saptamani>
            </angajat>
            <accident>
                <numar>1</numar>
                <victime>nu</victime>
                <daune>0</daune>
            </accident>
            <accident>
                <numar>3</numar>
                <victime>da</victime>
                <daune>5000</daune>
            </accident>
        </fabrica>
    
        <reguli>
            <regula1>
                <if>
                    <r1accident>X</r1accident>
                    <r1daune>10000</r1daune>
                    <operator>||</operator>
                    <r1saptamani>10</r1saptamani>
                </if>
                <then>
                    <r1accident>X</r1accident>
                    <gravitate>grav</gravitate>
                </then>
            </regula1>
            
            <regula1>
                <if>
                    <r1accident>X</r1accident>
                    <r1daune>5000</r1daune>
                    <operator>||</operator>
                    <r1saptamani>3</r1saptamani>
                </if>
                <then>
                    <r1accident>X</r1accident>
                    <gravitate>mediu</gravitate>
                </then>
            </regula1>
            
            <regula1>
                <if>
                    <r1accident>X</r1accident>
                    <r1daune>0</r1daune>
                    <operator>||</operator>
                    <r1saptamani>0</r1saptamani>
                </if>
                <then>
                    <r1accident>X</r1accident>
                    <gravitate>usor</gravitate>
                </then>
            </regula1>
            
            <regula2>
                <if>
                    <r2angajat>X</r2angajat>
                    <r2fabrica>Y</r2fabrica>
                    <salariu>max</salariu>
                </if>
                <then>
                    <r2angajat>X</r2angajat>
                    <r2fabrica>Y</r2fabrica>
                    <functie>sef</functie>
                </then>
                <else>
                    <r2angajat>X</r2angajat>
                    <r2fabrica>Y</r2fabrica>
                    <functie>angajat</functie>
                </else>
            </regula2>
        </reguli>
    
    </tema2>
    
    """;


            @BeforeEach
            public void setUp() throws Exception {
                page1Controller = new Page1Controller();
                interact(() -> {
                // Initialize the controller and the scene
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/page1.fxml"));
                    Parent root = null;
                    try {
                        root = fxmlLoader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    page1Controller = fxmlLoader.getController();

                Scene scene = new Scene(root, 1500, 800);
                Stage stage = new Stage();
                stage.setTitle("Page 1 Test");
                stage.setScene(scene);
                stage.show();
                });
            // Backup the original fabrici.xml file
            Files.copy(Paths.get(FABRICI_FILE_PATH), Paths.get(FABRICI_BACKUP_FILE_PATH));

            // Write the test XML data to fabrici.xml
            Files.write(Paths.get(FABRICI_FILE_PATH), TEST_XML_DATA.getBytes());
        }


        @AfterEach
        public void tearDown() throws Exception {
            // Restore the original fabrici.xml file
            Files.copy(Paths.get(FABRICI_BACKUP_FILE_PATH), Paths.get(FABRICI_FILE_PATH), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            Files.delete(Paths.get(FABRICI_BACKUP_FILE_PATH));
        }

        @Test
        void testDisplayAllEmployeesForValidFactory() {
            interact(() -> {
                page1Controller.listaAngajatiFabricaTextField.setText("Arctic");
                page1Controller.listaAngajatiFabricaTextFieldOnAction(null);
                String expectedOutput = """
                        Fabrica: Arctic
                        Oprea Alex 5000 lei
                        Buti Carina 4000 lei
                        Dinescu Nicolae 3800 lei
                        Tunaru Melania 7800 lei
                        """;
                assertEquals(expectedOutput.trim(), page1Controller.listaAngajatiFabricaTextArea.getText().trim());
            });
        }

        @Test
        void testDisplayMessageForNonExistingFactoryAngajati() {
            interact(() -> {
                page1Controller.listaAngajatiFabricaTextField.setText("Another");

               page1Controller.listaAngajatiFabricaTextFieldOnAction(null);

                String expectedOutput = "Nu exista o fabrica numita Another";
                assertEquals(expectedOutput.trim(), page1Controller.listaAngajatiFabricaTextArea.getText().trim());
            });
        }


        @Test
        void testDisplayAllEmployeesForEmptyFactoryName() {
            Platform.runLater(() -> {
                page1Controller.listaAngajatiFabricaTextField.setText("");
                page1Controller.listaAngajatiFabricaTextFieldOnAction(null);
                String expectedOutput =
                            """
                            Fabrica: Arctic
                            Oprea Alex 5000 lei
                            Buti Carina 4000 lei
                            Dinescu Nicolae 3800 lei
                            Tunaru Melania 7800 lei
                            
                            Fabrica: Dacia
                            Tabacu Ionut 6600 lei
                            Gherban Gabriel 4200 lei
                            Vasilica Florentina 5280 lei
                            
                            Fabrica: Fratii Schiel
                            Calin Madalina 5500 lei
                            Dirjan Zoe 4900 lei
                            Tudor Robert 5100 lei
                            
                            Fabrica: Astra
                            Barbu Valeriu 5600 lei
                            Pandaru Smaranda 3800 lei
                            Dragomir Florin 4400 lei
                            
                            Fabrica: Mekatronic
                            Tabacu Alfred 7000 lei
                            Slaboiu Narcisa 4100 lei
                            Nitu Valentin 4390 lei
                            """;
                assertEquals(expectedOutput.trim(), page1Controller.listaAngajatiFabricaTextArea.getText().trim());
            });
        }


        @Test
        void testDisplayAllAccidentsForValidFactory() {
            Platform.runLater(() -> {
                page1Controller.listaAccidenteFabricaTextField.setText("Arctic");
                page1Controller.listaAccidenteFabricaTextFieldOnAction(null);
                String expectedOutput = """
                        Fabrica: Arctic
                        Numar accident: 5
                        Victime: da
                        Valoare daune: 10000 lei
                        Angajati implicati:
                        Buti Carina 1 saptamani refacere
                        Dinescu Nicolae 10 saptamani refacere
                        """;
                assertEquals(expectedOutput.trim(), page1Controller.listaAccidenteFabricaTextArea.getText().trim());
            });
        }

        @Test
        void testDisplayMessageForNonExistingFactoryAccidente() {
            interact(() -> {
                page1Controller.listaAccidenteFabricaTextField.setText("Another");

                page1Controller.listaAccidenteFabricaTextFieldOnAction(null);

                String expectedOutput = "Fabrica Another nu exista";
                assertEquals(expectedOutput.trim(), page1Controller.listaAccidenteFabricaTextArea.getText().trim());
            });
        }


        @Test
        void testDisplayMessageForFactoryWithNoAccidents() {
            Platform.runLater(() -> {
                page1Controller.listaAccidenteFabricaTextField.setText("Fratii Schiel");
                page1Controller.listaAccidenteFabricaTextFieldOnAction(null);
                String expectedOutput = "Fabrica Fratii Schiel nu are inregistrate accidente";
                assertEquals(expectedOutput.trim(), page1Controller.listaAccidenteFabricaTextArea.getText().trim());
            });
        }

        @Test
        void testDisplayAllAccidentsForEmptyFactoryName() {
            Platform.runLater(() -> {
                page1Controller.listaAccidenteFabricaTextField.setText("");
                page1Controller.listaAccidenteFabricaTextFieldOnAction(null);
                String expectedOutput = """
                        Fabrica: Arctic
                        Numar accident: 5
                        Victime: da
                        Valoare daune: 10000 lei
                        Angajati implicati:
                        Buti Carina 1 saptamani refacere
                        Dinescu Nicolae 10 saptamani refacere
                                          
                        Fabrica: Dacia
                        Numar accident: 4
                        Victime: da
                        Valoare daune: 9999 lei
                        Angajati implicati:
                        Tabacu Ionut 10 saptamani refacere
                                          
                        Fabrica: Astra
                        Numar accident: 2
                        Victime: da
                        Valoare daune: 4999 lei
                        Angajati implicati:
                        Pandaru Smaranda 0 saptamani refacere
                        Dragomir Florin 3 saptamani refacere
                                          
                        Fabrica: Mekatronic
                        Numar accident: 1
                        Victime: nu
                        Valoare daune: 0 lei
                        Angajati implicati:
                        Nitu Valentin 0 saptamani refacere
                                          
                        Numar accident: 3
                        Victime: da
                        Valoare daune: 5000 lei
                        Angajati implicati:
                        Slaboiu Narcisa 2 saptamani refacere
                        """;
                assertEquals(expectedOutput.trim(), page1Controller.listaAccidenteFabricaTextArea.getText().trim());
            });
        }

        @Test
        void testComboBoxUsor() throws Exception {

            interact(() -> {
                page1Controller.gravitateAccidenteComboBox.setValue("usor");
                page1Controller.gravitateAccidenteComboBoxOnAction(null);


            String expectedOutput = """
                Numar: 1
                Victime: nu
                Daune: 0 lei
                """;
            assertEquals(expectedOutput.trim(), page1Controller.gravitateAccidenteTextArea.getText().trim());
             });
        }

        @Test
        void testComboBoxMediu() throws Exception {

            interact(() -> {
                page1Controller.gravitateAccidenteComboBox.setValue("mediu");
                page1Controller.gravitateAccidenteComboBoxOnAction(null);

            String expectedOutput = """
                Numar: 2
                Victime: da
                Daune: 4999 lei
                
                Numar: 3
                Victime: da
                Daune: 5000 lei
                """;
            assertEquals(expectedOutput.trim(), page1Controller.gravitateAccidenteTextArea.getText().trim());
            });
        }

        @Test
        void testComboBoxGrav() throws Exception {

            interact(() -> {
                page1Controller.gravitateAccidenteComboBox.setValue("grav");
                page1Controller.gravitateAccidenteComboBoxOnAction(null);


            String expectedOutput = """
                Numar: 5
                Victime: da
                Daune: 10000 lei
                
                Numar: 4
                Victime: da
                Daune: 9999 lei
                """;
            assertEquals(expectedOutput.trim(), page1Controller.gravitateAccidenteTextArea.getText().trim());
            });
        }





        // Teste auxiliare

        //Verify Handling of Large XML Files
        @Test
        void testHandlingOfLargeXMLFile() throws IOException {
            // Generate a large XML file
            StringBuilder largeXmlBuilder = new StringBuilder();
            largeXmlBuilder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><tema2>");
            for (int i = 0; i < 100000; i++) {
                largeXmlBuilder.append("<fabrica>")
                        .append("<nume>Factory").append(i).append("</nume>")
                        .append("<oras>City").append(i).append("</oras>")
                        .append("<angajat>")
                        .append("<prenume>First").append(i).append("</prenume>")
                        .append("<nume>Last").append(i).append("</nume>")
                        .append("<sex>m</sex>")
                        .append("<salariu>5000</salariu>")
                        .append("<nr_accident>0</nr_accident>")
                        .append("<saptamani>0</saptamani>")
                        .append("</angajat>")
                        .append("</fabrica>");
            }
            largeXmlBuilder.append("</tema2>");
            String largeXmlData = largeXmlBuilder.toString();
            Files.write(Paths.get(FABRICI_FILE_PATH), largeXmlData.getBytes());

            // Perform your test operations here
            interact(() -> {
                page1Controller.listaAngajatiFabricaTextField.setText("Factory80000");
                page1Controller.listaAngajatiFabricaTextFieldOnAction(null);
                String expectedOutput = "Factory80000";
                assertTrue(page1Controller.listaAngajatiFabricaTextArea.getText().contains(expectedOutput));
            });
        }

        @Test
        void testHandlingOfSpecialCharacters() throws IOException {
            String specialCharXmlData = """
    <?xml version="1.0" encoding="utf-8"?>
    <tema2>
        <fabrica>
            <nume>SpecialCharsFactory</nume>
            <oras>TestCity</oras>
            <angajat>
                <prenume>Alex!@#</prenume>
                <nume>O'Reilly</nume>
                <sex>m</sex>
                <salariu>5000</salariu>
                <nr_accident>0</nr_accident>
                <saptamani>0</saptamani>
            </angajat>
        </fabrica>
    </tema2>
    """;
            Files.write(Paths.get(FABRICI_FILE_PATH), specialCharXmlData.getBytes());

            // Perform your test operations here
            interact(() -> {
                page1Controller.listaAngajatiFabricaTextField.setText("SpecialCharsFactory");
                page1Controller.listaAngajatiFabricaTextFieldOnAction(null);
                String expectedOutput = "O'Reilly Alex!@# 5000 lei";
                assertEquals(expectedOutput.trim(), page1Controller.listaAngajatiFabricaTextArea.getText().trim());
            });
        }

        @Test
        void testHandlingOfMalformedXML() throws IOException {
            String malformedXmlData = """
    <?xml version="1.0" encoding="utf-8"?>
    <tema2>
        <fabrica>
            <nume>MalformedFactory<nume>
            <oras>TestCity
            <angajat>
                <prenume>Alex</prenume>
                <nume>Oprea</nume>
                <sex>m</sex>
                <salariu>5000</salariu>
                <nr_accident>0</nr_accident>
                <saptamani>0</saptamani>
            </angajat>
        </fabrica>
    </tema2>
    """;
            Files.write(Paths.get(FABRICI_FILE_PATH), malformedXmlData.getBytes());

            // Perform your test operations here
            interact(() -> {
                page1Controller.listaAngajatiFabricaTextField.setText("MalformedFactory");
                try {
                    page1Controller.listaAngajatiFabricaTextFieldOnAction(null);
                    fail("Expected an exception due to malformed XML.");
                } catch (Exception e) {
                    assertTrue(e.getMessage().contains("Malformed XML"));
                }
            });
        }




    }