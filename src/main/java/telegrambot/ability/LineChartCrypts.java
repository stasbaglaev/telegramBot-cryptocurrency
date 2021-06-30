package telegrambot.ability;

import java.io.*;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import telegrambot.service.MessageRecipientService;

public class LineChartCrypts {
    private static final Logger LOGGER = LogManager.getLogger(MessageRecipientService.class);
    @Getter
    public final String fileName = "LineChartCrypt.jpeg";

    public void getLineChartCrypts() {
        DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();
        line_chart_dataset.addValue(15, "стоимость", "1970");
        line_chart_dataset.addValue(30, "стоимость", "1980");
        line_chart_dataset.addValue(60, "стоимость", "1990");
        line_chart_dataset.addValue(120, "стоимость", "2000");
        line_chart_dataset.addValue(240, "стоимость", "2010");
        line_chart_dataset.addValue(300, "стоимость", "2014");

        JFreeChart lineChartObject = ChartFactory.createLineChart(
                "Курс биткоина", "Период",
                "Стоимость криптовалюты",
                line_chart_dataset, PlotOrientation.VERTICAL,
                true, true, false);

        int width = 640;
        int height = 480;

        File lineChart = new File(fileName);
        try {
            ChartUtilities.saveChartAsJPEG(lineChart, lineChartObject, width, height);
        } catch (IOException e) {
            LOGGER.debug("Can't save lineChart: " + lineChart);
        }
    }
}