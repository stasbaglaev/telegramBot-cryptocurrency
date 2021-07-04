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
import telegrambot.service.database.ConnectionSql;
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
    public static final String FILE_NAME = "LineChartCrypt.jpeg";
    //private static final File BACKGROUND_IMAGE = new File("BackgroundImage.jpg");
    //private static final File BACKGROUND_CHART = new File("BackgroundChart.jpg");
    //private static final String nameCrypt = "ETC";
    private static final DefaultCategoryDataset lineChartDataset = new DefaultCategoryDataset();
    private static Boolean completed = true;
    @Getter
    private static Image imageBackground = null;
    @Getter
    private static Image chartBackground = null;

    static {
        try {
            imageBackground = ImageIO.read(new File("BackgroundImage.jpg"));
            chartBackground = ImageIO.read(new File("BackgroundChart.jpg"));
        } catch (IOException e) {
            LOGGER.warn("File 'BackgroundChart.jpg' or File 'BackgroundImage.jpg' not found in project folder!");
        }
    }


    private static final List<String> columnCurrency = Arrays.asList("currencyUSD", "currencyEUR", "currencyRUB");

    public static String getLineChartCrypts(String nameCrypt) {
        completed = false;
        try (ResultSet resultSet = select(nameCrypt)) {
            try {
                while (true) {
                    assert resultSet != null;
                    if (!resultSet.next()) break;
                    //String name = resultSet.getString("name");
                    String date = resultSet.getString("date");
                    String dayTime = date.substring(0, 2);
                    for (String currency : columnCurrency) {
                        int value = resultSet.getInt(currency);
                        lineChartDataset.addValue(value, currency, date);
                    }
                    createLineChart(nameCrypt);
                }
            } catch (SQLException e) {
                LOGGER.warn("Error! ");
            }
        } catch (SQLException e) {
            LOGGER.warn("Error! The request is being generated, but there may be an error in the structure of the database table");
        }
        return "";
    }

    private static ResultSet select(String nameCrypt) {
        String sqlQuery = "SELECT name, date, currencyUSD, currencyEUR, currencyRUB FROM crypts where name = ?";
        try {
            PreparedStatement preparedStatement = ConnectionSql.getConnection().prepareStatement(sqlQuery);
            preparedStatement.setString(1, nameCrypt);
            return preparedStatement.executeQuery();

        } catch (SQLException e) {
            LOGGER.warn("I can not complete the request: " + sqlQuery);
        }
        return null;
    }

    private static void createLineChart(String nameCrypt) {
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
        File lineChart = new File(FILE_NAME);
        try {
            ChartUtilities.saveChartAsJPEG(lineChart, lineChartObject, width, height);
        } catch (IOException e) {
            LOGGER.warn("Can't save lineChart: " + lineChart);
        }
        completed = true;
    }

    private static void makeSettingsLineChart(JFreeChart lineChartObject) {
        CategoryAxis domainAxis = lineChartObject.getCategoryPlot().getDomainAxis();
        domainAxis.setTickLabelFont(new Font("Dialog", Font.PLAIN, 7));
        lineChartObject.setBackgroundImage(getImageBackground());
        Plot plot = lineChartObject.getPlot();
        plot.setBackgroundImage(getChartBackground());
//            plot.setBackgroundImageAlignment(Align.TOP_RIGHT);
//            plot.setBackgroundImageAlpha(1.0f);
    }

    public Boolean completedOperation() {
        return completed;
    }
}