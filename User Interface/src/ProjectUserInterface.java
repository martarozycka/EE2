import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.concurrent.ExecutionException;

public class ProjectUserInterface extends JFrame {
    private JPanel panel1;
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

    public ProjectUserInterface(String title){
        super(title);

        // add restriction so that min and max cant be bigger/smaller than actual value
        // optional: make 3 progress bars: 1 bar form a value lower than min to min, 1 from min to max and 1 from max to a higher value
        setContentPane(panel1);

        minTempSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                minTemp = Integer.parseInt(minTempValue.getText());
                JSlider source = (JSlider) e.getSource();
                minTempValue.setText(String.valueOf(source.getValue()));
                tempProgressBar.setMinimum(minTemp - 20);
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
                tempProgressBar.setMaximum(maxTemp + 20);
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
                JSlider source = (JSlider) e.getSource();
                minPresValue.setText(String.valueOf(source.getValue()));
                presProgressBar.setMinimum(minPres - 20);
                if (Integer.parseInt(presValue.getText()) < minPres){
                    presProgressBar.setForeground(Color.GRAY);
                    } else if (Integer.parseInt(presValue.getText()) >= maxPres) {
                    presProgressBar.setForeground(Color.RED);
                }
                else {
                    presProgressBar.setForeground(Color.GREEN);
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
                presProgressBar.setMaximum(maxPres + 20);
                if (Integer.parseInt(presValue.getText()) < minPres){
                    presProgressBar.setForeground(Color.GRAY);
                } else if (Integer.parseInt(presValue.getText()) >= maxPres) {
                    presProgressBar.setForeground(Color.RED);
                }
                else {
                    presProgressBar.setForeground(Color.GREEN);
                }
                String mint = "/" + minTempValue.getText();
                String maxt = "/" + maxTempValue.getText();
                String minp = "/" + minPresValue.getText();
                String maxp = "/" + maxPresValue.getText();
                db.makeGETRequest("https://studev.groept.be/api/a22ib2c01/insertMinMax" + mint + maxt + minp + maxp);
            }
        });



        // dummy data to check interface, actual value will be collected from database (sprint 3)
        Integer[] measurements_value = db.parseJSON(db.makeGETRequest("https://studev.groept.be/api/a22ib2c01/selectLatestMeasurements"));
        tempValue.setText(String.valueOf(measurements_value[1]));
        presValue.setText(String.valueOf(measurements_value[2]));
        tempProgressBar.setValue(Integer.parseInt(tempValue.getText()));
        presProgressBar.setValue(Integer.parseInt(presValue.getText()));
        //init pressBar
        minPres = 0;
        presProgressBar.setMinimum(minPres - 20);
        if (Integer.parseInt(presValue.getText()) < minPres){
            presProgressBar.setForeground(Color.GRAY);
        } else if (Integer.parseInt(presValue.getText()) >= maxPres) {
            presProgressBar.setForeground(Color.RED);
        }
        else {
            presProgressBar.setForeground(Color.GREEN);
        }

        maxPres = 100;
        presProgressBar.setMaximum(maxPres + 20);
        if (Integer.parseInt(presValue.getText()) < minPres){
            presProgressBar.setForeground(Color.GRAY);
        } else if (Integer.parseInt(presValue.getText()) >= maxPres) {
            presProgressBar.setForeground(Color.RED);
        }
        else {
            presProgressBar.setForeground(Color.GREEN);
        }

        //init tempBar
        minTemp = 0;
        tempProgressBar.setMinimum(minTemp - 20);
        if (Integer.parseInt(tempValue.getText()) < minTemp){
            tempProgressBar.setForeground(Color.GRAY);
        } else if (Integer.parseInt(tempValue.getText()) >= maxTemp) {
            tempProgressBar.setForeground(Color.RED);
        }
        else {
            tempProgressBar.setForeground(Color.GREEN);
        }

        maxTemp = 100;
        tempProgressBar.setMaximum(maxTemp + 20);
        if (Integer.parseInt(tempValue.getText()) < minTemp){
            tempProgressBar.setForeground(Color.GRAY);
        } else if (Integer.parseInt(tempValue.getText()) >= maxTemp) {
            tempProgressBar.setForeground(Color.RED);
        }
        else {
            tempProgressBar.setForeground(Color.GREEN);
        }


    }

    public static void main(String[] args) {

        JFrame ui = new ProjectUserInterface(("My title"));
        ui.setVisible(true);
        ui.setSize(600,200);
        ui.setLocationRelativeTo(null);
    }


}
