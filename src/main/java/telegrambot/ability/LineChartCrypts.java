package telegrambot.ability;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import service.database.ConnectionSQL;
import telegrambot.service.MessageRecipientService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class LineChartCrypts {

    private static final Logger LOGGER = LogManager.getLogger(MessageRecipientService.class);
    @Getter
    public static final String fileName = "LineChartCrypt.jpeg";
    private static final File background = new File("Background.jpg");
    private static final String nameCrypt = "ETC";
    private static  DefaultCategoryDataset lineChartDataset = new DefaultCategoryDataset();

    List<String> columnCurrency = Arrays.asList("currencyUSD", "currencyEUR", "currencyRUB");

    public void getLineChartCrypts() {
        try (ResultSet resultSet = select()){
        try {
            while (true) {
                assert resultSet != null;
                if (!resultSet.next()) break;
                //String name = resultSet.getString("name");
                String date = resultSet.getString("date");
                for (String currency : columnCurrency) {
                    int value = resultSet.getInt(currency);
                    lineChartDataset.addValue(value, currency, date);
                }
                createLineChart();
            }
        } catch (SQLException throwables) {
            LOGGER.warn("Error! ");
        }
        } catch (SQLException e) {
            LOGGER.warn("Error! The request is being generated, but there may be an error in the structure of the database table");
        }
    }

    private static ResultSet select() {
        String sqlQuery = "SELECT name, date, currencyUSD, currencyEUR, currencyRUB FROM crypts where name = ?";
        try {
            PreparedStatement preparedStatement = ConnectionSQL.getConnection().prepareStatement(sqlQuery);
                preparedStatement.setString(1, nameCrypt);
                return preparedStatement.executeQuery();

        } catch (SQLException e) {
            LOGGER.warn("I can not complete the request: " + sqlQuery);
        }
        return null;
    }

    private static void createLineChart() {
        JFreeChart lineChartObject = ChartFactory.createLineChart(
                "Курс " + nameCrypt, "Период",
                "Стоимость",
                lineChartDataset, PlotOrientation.VERTICAL,
                true, true, false);

        makeSettingsLineChart(lineChartObject);
        saveChartAsJpeg(lineChartObject);
    }

    private static void saveChartAsJpeg(JFreeChart lineChartObject) {
        int width = 1280;
        int height = 720;
        File lineChart = new File(fileName);
        try {
            ChartUtilities.saveChartAsJPEG(lineChart, lineChartObject, width, height);
        } catch (IOException e) {
            LOGGER.warn("Can't save lineChart: " + lineChart);
        }
    }

    private static void makeSettingsLineChart(JFreeChart lineChartObject) {
        CategoryAxis domainAxis = lineChartObject.getCategoryPlot().getDomainAxis();
        domainAxis.setTickLabelFont(new Font("Dialog", Font.PLAIN, 7));

        try {
            lineChartObject.setBackgroundImage(ImageIO.read(background));
            Plot plot = lineChartObject.getPlot();
            plot.setBackgroundImage(ImageIO.read(background));
            plot.setBackgroundImageAlpha(0.5f);
        } catch (IOException e) {
            LOGGER.warn("File 'Background.jpg' not found in project folder!");
        }
    }
}