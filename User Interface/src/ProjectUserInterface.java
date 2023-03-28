import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import javax.imageio.ImageIO;
import java.io.File;
public class ProjectUserInterface extends JFrame {
    private JPanel panel1,panel2;
    private JProgressBar presProgressBar;
    private JProgressBar tempProgressBar;
    private JSlider minPresSlider;
    private JSlider maxPresSlider;
    private JSlider maxTempSlider;
    private JSlider minTempSlider;
    private JLabel minTempValue;
    private JLabel maxTempValue;
    private JLabel minPresValue;
    private JLabel maxPresValue;
    private JLabel tempValue;
    private JLabel presValue;
    private JLabel myTemperature;
    private JLabel myPressure;
    private JLabel myTitle;
    private JLabel minLabelTemp;
    private JLabel maxLabelTemp;
    private JLabel minLabelPres;
    private JLabel maxLabelPres;
    private JLabel presUnit;
    private JLabel tempUnit;
    private int minPres;
    private int maxPres;
    private int minTemp;
    private int maxTemp;

    DBTest db = new DBTest();

    public void setLabels(){
        Integer[] measurements_value = db.parseJSON(db.makeGETRequest("https://studev.groept.be/api/a22ib2c01/selectLatestMeasurements"));
        tempValue.setText(String.valueOf(measurements_value[1]));
        presValue.setText(String.valueOf(measurements_value[2]));
        tempProgressBar.setValue(Integer.parseInt(tempValue.getText()));
        presProgressBar.setValue(Integer.parseInt(presValue.getText()));

        presProgressBar.setMinimum(minPres * 2 - maxPres);
        if (Integer.parseInt(presValue.getText()) < minPres) {
            presProgressBar.setForeground(Color.GRAY);
        } else if (Integer.parseInt(presValue.getText()) >= maxPres) {
            presProgressBar.setForeground(new Color(224, 47, 20));
        } else {
            presProgressBar.setForeground(new Color(32, 199, 71));
        }
        presProgressBar.setMaximum(maxPres * 2 - minPres);
        if (Integer.parseInt(presValue.getText()) < minPres) {
            presProgressBar.setForeground(Color.GRAY);
        } else if (Integer.parseInt(presValue.getText()) >= maxPres) {
            presProgressBar.setForeground(new Color(224, 47, 20));
        } else {
            presProgressBar.setForeground(new Color(32, 199, 71));
        }

    }

    public ProjectUserInterface(String title) {

        super(title);

        // add restriction so that min and max cant be bigger/smaller than actual value
        // optional: make 3 progress bars: 1 bar form a value lower than min to min, 1 from min to max and 1 from max to a higher value
        ImageIcon bg=new ImageIcon("src/icon/children-eating.jpg");
        JLabel label1=new JLabel(bg);
        label1.setSize(bg.getIconWidth(),bg.getIconHeight());
        this.getLayeredPane().add(label1,0);
        setContentPane(panel1);
        panel1.setBackground(new Color(245,231,177));
        //setContentPane(panel2);

        minTempSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                minTemp = Integer.parseInt(minTempValue.getText());
                JSlider source = (JSlider) e.getSource();
                minTempValue.setText(String.valueOf(source.getValue()));
                if (maxTemp<minTemp || minTemp>maxTemp) {
                    JOptionPane.showMessageDialog(null,"Minimum value larger than maximum value");
                }
                tempProgressBar.setMinimum(minTemp * 2 - maxTemp);//1/3

                if (Integer.parseInt(tempValue.getText()) < minTemp) {
                    tempProgressBar.setForeground(new Color(20, 133, 224));//blue
                } else if (Integer.parseInt(tempValue.getText()) >= maxTemp) {
                    tempProgressBar.setForeground(new Color(224, 47, 20));
                } else {
                    tempProgressBar.setForeground(new Color(32, 199, 71));
                }
                String mint = "/" + minTempValue.getText();
                String maxt = "/" + maxTempValue.getText();
                String minp = "/" + minPresValue.getText();
                String maxp = "/" + maxPresValue.getText();
                db.makeGETRequest("https://studev.groept.be/api/a22ib2c01/insertMinMax" + mint + maxt + minp + maxp);
            }
        });
        maxTempSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                maxTemp = Integer.parseInt(maxTempValue.getText());
                JSlider source = (JSlider) e.getSource();
                maxTempValue.setText(String.valueOf(source.getValue()));
                if (maxTemp<minTemp || minTemp>maxTemp) {
                    JOptionPane.showMessageDialog(null,"Maximum value smaller than minimum value");
                }
                tempProgressBar.setMaximum(maxTemp * 2 - minTemp);//1/3
                if (Integer.parseInt(tempValue.getText()) < minTemp) {
                    tempProgressBar.setForeground(new Color(20, 133, 224));
                } else if (Integer.parseInt(tempValue.getText()) >= maxTemp) {
                    tempProgressBar.setForeground(new Color(224, 47, 20));//red
                } else {
                    tempProgressBar.setForeground(new Color(32, 199, 71));
                }
                String mint = "/" + minTempValue.getText();
                String maxt = "/" + maxTempValue.getText();
                String minp = "/" + minPresValue.getText();
                String maxp = "/" + maxPresValue.getText();
                db.makeGETRequest("https://studev.groept.be/api/a22ib2c01/insertMinMax" + mint + maxt + minp + maxp);
            }
        });
        minPresSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                minPres = Integer.parseInt(minPresValue.getText());
                if (minPres>maxPres || maxPres<minPres) {
                    JOptionPane.showMessageDialog(null,"Minimum value larger than maximum value");
                }
                JSlider source = (JSlider) e.getSource();
                minPresValue.setText(String.valueOf(source.getValue()));
                presProgressBar.setMinimum(minPres * 2 - maxPres);
                if (Integer.parseInt(presValue.getText()) < minPres) {
                    presProgressBar.setForeground(Color.GRAY);
                } else if (Integer.parseInt(presValue.getText()) >= maxPres) {
                    presProgressBar.setForeground(new Color(224, 47, 20));
                } else {
                    presProgressBar.setForeground(new Color(32, 199, 71));
                }
                String mint = "/" + minTempValue.getText();
                String maxt = "/" + maxTempValue.getText();
                String minp = "/" + minPresValue.getText();
                String maxp = "/" + maxPresValue.getText();
                db.makeGETRequest("https://studev.groept.be/api/a22ib2c01/insertMinMax" + mint + maxt + minp + maxp);
            }
        });
        maxPresSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                maxPres = Integer.parseInt(maxPresValue.getText());
                JSlider source = (JSlider) e.getSource();
                maxPresValue.setText(String.valueOf(source.getValue()));
                if (maxPres<minPres || minPres>maxPres) {
                    JOptionPane.showMessageDialog(null,"Maximum value smaller than minimum value");
                }
                presProgressBar.setMaximum(maxPres * 2 - minPres);
                if (Integer.parseInt(presValue.getText()) < minPres) {
                    presProgressBar.setForeground(Color.GRAY);
                } else if (Integer.parseInt(presValue.getText()) >= maxPres) {
                    presProgressBar.setForeground(new Color(224, 47, 20));
                } else {
                    presProgressBar.setForeground(new Color(32, 199, 71));
                }
                String mint = "/" + minTempValue.getText();
                String maxt = "/" + maxTempValue.getText();
                String minp = "/" + minPresValue.getText();
                String maxp = "/" + maxPresValue.getText();
                db.makeGETRequest("https://studev.groept.be/api/a22ib2c01/insertMinMax" + mint + maxt + minp + maxp);
            }
        });

        //init pressBar
        maxPres = 100;
        minPres = 0;
        //presProgressBar.set
        presProgressBar.setMinimum(minPres * 2 - maxPres);
        presValue.setText("0");
        if (Integer.parseInt(presValue.getText()) < minPres) {
            presProgressBar.setForeground(Color.GRAY);
        } else if (Integer.parseInt(presValue.getText()) >= maxPres) {
            presProgressBar.setForeground(new Color(224, 47, 20));
        } else {
            presProgressBar.setForeground(new Color(32, 199, 71));
        }
        maxPres = 100;
        minPres = 0;
        presProgressBar.setMaximum(maxPres * 2 - minPres);
        if (Integer.parseInt(presValue.getText()) < minPres) {
            presProgressBar.setForeground(Color.GRAY);
        } else if (Integer.parseInt(presValue.getText()) >= maxPres) {
            presProgressBar.setForeground(new Color(224, 47, 20));
        } else {
            presProgressBar.setForeground(new Color(32, 199, 71));
        }

        //init tempBar
        minTemp = 0;
        maxTemp = 100;
        tempProgressBar.setMinimum(minTemp * 2 - maxTemp);
        tempValue.setText("0");
        if (Integer.parseInt(tempValue.getText()) < minTemp) {
            tempProgressBar.setForeground(new Color(20, 133, 224));
        } else if (Integer.parseInt(tempValue.getText()) >= maxTemp) {
            tempProgressBar.setForeground(new Color(224, 47, 20));
        } else {
            tempProgressBar.setForeground(new Color(32, 199, 71));
        }
        minTemp = 0;
        maxTemp = 100;
        tempProgressBar.setMaximum(maxTemp * 2 - minTemp);
        if (Integer.parseInt(tempValue.getText()) < minTemp) {
            tempProgressBar.setForeground(new Color(20, 133, 224));
        } else if (Integer.parseInt(tempValue.getText()) >= maxTemp) {
            tempProgressBar.setForeground(new Color(224, 47, 20));
        } else {
            tempProgressBar.setForeground(new Color(32, 199, 71));
        }



    }

        public static void main (String[]args){

            ProjectUserInterface ui = new ProjectUserInterface(("  ~ SHOON ~"));

            //JPanel pan=(JPanel)ui.getContentPane();

//            ui.getLayeredPane().add(,1);
            ui.setSize(915, 650);
            //pan.setOpaque(false);
//            pan.setLayout(new FlowLayout());

            //ui.setSize(bg.getIconWidth(),bg.getIconHeight());


            //ui.setUndecorated(true);
            //ui.setBackground(new Color(0,0,0,0));
            ui.setLocationRelativeTo(null);
            ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ui.setVisible(true);

            while(true){
                ui.setLabels();
            }
        }


}
