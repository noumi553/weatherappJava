import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class weatherproject {
    public static void main(String[] args) {
        JFrame window = new JFrame("Weather App");
        window.setSize(600, 700);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new FlowLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1, 10, 10));

        JLabel header = new JLabel("Weather App", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(header);

        JTextField input = new JTextField(25);
        panel.add(input);

        JButton getWeatherData = new JButton("Get Weather Data");
        getWeatherData.setBackground(Color.BLUE);
        getWeatherData.setForeground(Color.WHITE);
        getWeatherData.setFont(new Font("Calibri", Font.ITALIC, 17));
        panel.add(getWeatherData);

        JLabel labelCity = new JLabel("City: ");
        labelCity.setForeground(Color.WHITE);
        labelCity.setFont(new Font("Calibri" ,Font.ITALIC, 17));
        JLabel labelCountry = new JLabel("Country: ");
        labelCountry.setForeground(Color.WHITE);
        labelCountry.setFont(new Font("Calibri" ,Font.ITALIC, 17));
        JLabel labelCloud = new JLabel("Cloud: ");
        labelCloud.setForeground(Color.WHITE);
        labelCloud.setFont(new Font("Calibri" ,Font.ITALIC, 17));
        JLabel labelCondition = new JLabel("Condition: ");
        labelCondition.setForeground(Color.WHITE);
        labelCondition.setFont(new Font("Calibri" ,Font.ITALIC, 17));
        JLabel tempC = new JLabel("Temp (°C): ");
        tempC.setForeground(Color.WHITE);
        tempC.setFont(new Font("Calibri" ,Font.ITALIC, 17));
        JLabel tempF = new JLabel("Temp (°F): ");
        tempF.setFont(new Font("Calibri" ,Font.ITALIC, 17));
        tempF.setForeground(Color.WHITE);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(0,2,5,5));
        panel2.setBackground(Color.darkGray);

        JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayout(0,2,5,5));
        panel3.setBackground(Color.darkGray);

        JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayout(0,2,5,5));
        panel4.setBackground(Color.darkGray);

        panel4.add(labelCity);
        panel4.add(labelCountry);
        panel3.add(labelCloud);
        panel3.add(labelCondition);
        panel2.add(tempC);
        panel2.add(tempF);

        panel.add(panel4);
        panel.add(panel3);
        panel.add(panel2);

        

        window.add(panel);
        window.setVisible(true);

        getWeatherData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String inputData = URLEncoder.encode(input.getText(), "UTF-8");
                    String apiKey = "6d395f0a50ad434f905115045241708";
                    String urlString = "https://api.weatherapi.com/v1/current.json?key=" + apiKey + "&q=" + inputData;

                    URL url = new URL(urlString);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");

                    BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    String json = response.toString();

                    String cityName = json.split("\"name\":")[1].split(",")[0].replace("\"", "").trim();
                    String country = json.split("\"country\":")[1].split(",")[0].replace("\"", "").trim();
                    String cloud = json.split("\"cloud\":")[1].split(",")[0].trim();
                    String condition = json.split("\"text\":")[1].split(",")[0].replace("\"", "").trim();
                    String temp_c = json.split("\"temp_c\":")[1].split(",")[0].trim();
                    String temp_f = json.split("\"temp_f\":")[1].split(",")[0].trim();

                    labelCity.setText("City: " + cityName);
                    labelCountry.setText("Country: " + country);
                    labelCloud.setText("Cloud: " + cloud + "%");
                    labelCondition.setText("Condition: " + condition);
                    tempC.setText("Temp (°C): " + temp_c);
                    tempF.setText("Temp (°F): " + temp_f);

                    panel.revalidate();
                    panel.repaint();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(window, "Error fetching data: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
    }
}
