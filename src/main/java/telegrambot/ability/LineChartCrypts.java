package telegrambot.ability;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import telegrambot.service.MessageRecipientService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.*;

public class LineChartCrypts {
    private static final Logger LOGGER = LogManager.getLogger(MessageRecipientService.class);
    @Getter
    public final String fileName = "LineChartCrypt.jpeg";
    private final File background = new File("Background.jpg");
    private static Connection connection;
    private static String nameCrypt = "ETC";


    private static void connectSQL() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_db", "wwroot", "1qaz@WSX");

        } catch (SQLException e) {
            LOGGER.warn("Error connection on DB 'my_db' ");
        }
    }

    private static ResultSet select() {
        String sqlQuery = "SELECT name, date, currency FROM crypts where name = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, nameCrypt);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            LOGGER.warn("I can not complete the request: " + sqlQuery);
        }
        return null;
    }


    public void getLineChartCrypts() {
        connectSQL();
        ResultSet resultSet = select();
        DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();
        try {
            while (true) {
                assert resultSet != null;
                if (!resultSet.next()) break;
                String name = resultSet.getString("name");
                String date = resultSet.getString("date");
                int currency = resultSet.getInt("currency");
                line_chart_dataset.addValue(currency, name, date);
            }
            JFreeChart lineChartObject = ChartFactory.createLineChart(
                    "Курс " + nameCrypt, "Период",
                    "Стоимость",
                    line_chart_dataset, PlotOrientation.VERTICAL,
                    true, true, false);

            try {
                lineChartObject.setBackgroundImage(ImageIO.read(background));
            } catch (IOException e) {
                LOGGER.warn("File 'Background.jpg' not found in project folder!");
            }

            Plot plot = lineChartObject.getPlot();
            plot.setBackgroundImageAlpha(0.5f);
            try {
                plot.setBackgroundImage(ImageIO.read(background));
            } catch (IOException e) {
                LOGGER.warn("File 'Background.jpg' not found in project folder!");
            }
            Font font3 = new Font("Dialog", Font.PLAIN, 7);
            CategoryPlot plotik = lineChartObject.getCategoryPlot();
            CategoryAxis domainAxis = plotik.getDomainAxis();
            domainAxis.setTickLabelFont(font3);

            int width = 1280;
            int height = 720;

            File lineChart = new File(fileName);
            try {
                ChartUtilities.saveChartAsJPEG(lineChart, lineChartObject, width, height);
            } catch (IOException e) {
                LOGGER.warn("Can't save lineChart: " + lineChart);
            }
        } catch (SQLException e) {
            LOGGER.warn("Error! The request is being generated, but there may be an error in the structure of the database table");
        }
    }
}