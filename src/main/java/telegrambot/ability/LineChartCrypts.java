package telegrambot.ability;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import telegrambot.service.MessageRecipientService;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class LineChartCrypts {
    private static final Logger LOGGER = LogManager.getLogger(MessageRecipientService.class);
    @Getter
    public final String fileName = "LineChartCrypt.jpeg";
    private final File background = new File("Background.jpg");

    public void getLineChartCrypts() {
        DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();
        line_chart_dataset.addValue(15, "Биткоин", "25.06.21");
        line_chart_dataset.addValue(30, "Биткоин", "26.06.21");
        line_chart_dataset.addValue(60, "Биткоин", "27.06.21");
        line_chart_dataset.addValue(300, "Биткоин", "28.06.21");
        line_chart_dataset.addValue(240, "Биткоин", "29.06.21");
        line_chart_dataset.addValue(235, "Биткоин", "30.06.21");

        JFreeChart lineChartObject = ChartFactory.createLineChart(
                "Курс криптовалюты", "Период",
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