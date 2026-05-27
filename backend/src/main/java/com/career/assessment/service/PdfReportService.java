package com.career.assessment.service;

import com.career.assessment.dto.AssessmentResultDTO;
import com.career.assessment.dto.CareerRecommendationDTO;
import com.career.assessment.dto.CategoryScoreDTO;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.SpiderWebPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PdfReportService {

    private static final DeviceRgb PRIMARY_COLOR = new DeviceRgb(41, 98, 255);
    private static final DeviceRgb SECONDARY_COLOR = new DeviceRgb(0, 184, 148);
    private static final DeviceRgb ACCENT_COLOR = new DeviceRgb(253, 121, 168);
    private static final DeviceRgb DARK_COLOR = new DeviceRgb(45, 52, 54);
    private static final DeviceRgb LIGHT_BG = new DeviceRgb(245, 246, 250);
    private static final DeviceRgb WHITE = new DeviceRgb(255, 255, 255);

    public byte[] generateReport(AssessmentResultDTO result) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4);
        document.setMargins(40, 40, 40, 40);

        PdfFont boldFont;
        PdfFont regularFont;
        try {
            boldFont = PdfFontFactory.createFont("Helvetica-Bold");
            regularFont = PdfFontFactory.createFont("Helvetica");
        } catch (IOException e) {
            throw new RuntimeException("Failed to create fonts", e);
        }

        addCoverPage(document, result, boldFont, regularFont);
        document.add(new AreaBreak());

        addOverviewSection(document, result, boldFont, regularFont);
        document.add(new AreaBreak());

        addBarChart(document, result.getCategoryScores(), boldFont, regularFont);
        document.add(new AreaBreak());

        addPieChart(document, result.getCategoryScores(), boldFont, regularFont);
        document.add(new AreaBreak());

        addCategoryDetails(document, result.getCategoryScores(), boldFont, regularFont);
        document.add(new AreaBreak());

        addCareerRecommendations(document, result.getCareerRecommendations(), boldFont, regularFont);
        document.add(new AreaBreak());

        addDevelopmentPlan(document, result, boldFont, regularFont);

        document.close();
        return baos.toByteArray();
    }

    private void addCoverPage(Document doc, AssessmentResultDTO result, PdfFont bold, PdfFont regular) {
        doc.add(new Paragraph("\n\n\n\n"));

        doc.add(new Paragraph("CAREER ASSESSMENT")
                .setFont(bold).setFontSize(36).setFontColor(PRIMARY_COLOR)
                .setTextAlignment(TextAlignment.CENTER));

        doc.add(new Paragraph("DETAILED REPORT")
                .setFont(bold).setFontSize(24).setFontColor(SECONDARY_COLOR)
                .setTextAlignment(TextAlignment.CENTER));

        doc.add(new Paragraph("\n"));

        Table infoTable = new Table(UnitValue.createPercentArray(new float[]{1, 2}))
                .setWidth(UnitValue.createPercentValue(60))
                .setHorizontalAlignment(HorizontalAlignment.CENTER);

        addInfoRow(infoTable, "Name:", result.getUserName(), bold, regular);
        addInfoRow(infoTable, "Email:", result.getEmail(), bold, regular);
        addInfoRow(infoTable, "Session:", result.getSessionCode(), bold, regular);
        if (result.getCompletedAt() != null) {
            addInfoRow(infoTable, "Date:", result.getCompletedAt()
                    .format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")), bold, regular);
        }

        doc.add(infoTable);

        doc.add(new Paragraph("\n\n"));
        doc.add(new Paragraph("5-Dimensional Career Assessment")
                .setFont(regular).setFontSize(14).setFontColor(DARK_COLOR)
                .setTextAlignment(TextAlignment.CENTER));
        doc.add(new Paragraph("Orientation Style | Interest | Personality | Aptitude | Emotional Quotient")
                .setFont(regular).setFontSize(11).setFontColor(new DeviceRgb(120, 120, 120))
                .setTextAlignment(TextAlignment.CENTER));
    }

    private void addInfoRow(Table table, String label, String value, PdfFont bold, PdfFont regular) {
        table.addCell(new Cell().add(new Paragraph(label).setFont(bold).setFontSize(12))
                .setBorder(Border.NO_BORDER).setPadding(5));
        table.addCell(new Cell().add(new Paragraph(value).setFont(regular).setFontSize(12))
                .setBorder(Border.NO_BORDER).setPadding(5));
    }

    private void addOverviewSection(Document doc, AssessmentResultDTO result, PdfFont bold, PdfFont regular) {
        doc.add(new Paragraph("ASSESSMENT OVERVIEW")
                .setFont(bold).setFontSize(22).setFontColor(PRIMARY_COLOR)
                .setBorderBottom(new SolidBorder(PRIMARY_COLOR, 2)).setPaddingBottom(10));

        doc.add(new Paragraph("\n"));
        doc.add(new Paragraph(result.getOverallSummary())
                .setFont(regular).setFontSize(12).setFontColor(DARK_COLOR));
        doc.add(new Paragraph("\n"));

        Table scoreTable = new Table(UnitValue.createPercentArray(new float[]{2, 1, 1, 1}))
                .setWidth(UnitValue.createPercentValue(100));

        scoreTable.addHeaderCell(createHeaderCell("Category", bold));
        scoreTable.addHeaderCell(createHeaderCell("Score", bold));
        scoreTable.addHeaderCell(createHeaderCell("Max Score", bold));
        scoreTable.addHeaderCell(createHeaderCell("Percentage", bold));

        for (CategoryScoreDTO score : result.getCategoryScores()) {
            scoreTable.addCell(createDataCell(score.getCategoryName(), regular));
            scoreTable.addCell(createDataCell(String.valueOf(score.getRawScore()), regular));
            scoreTable.addCell(createDataCell(String.valueOf(score.getMaxScore()), regular));
            scoreTable.addCell(createDataCell(score.getPercentage() + "%", regular));
        }

        doc.add(scoreTable);
    }

    private Cell createHeaderCell(String text, PdfFont font) {
        return new Cell().add(new Paragraph(text).setFont(font).setFontSize(11).setFontColor(WHITE))
                .setBackgroundColor(PRIMARY_COLOR).setPadding(8)
                .setTextAlignment(TextAlignment.CENTER);
    }

    private Cell createDataCell(String text, PdfFont font) {
        return new Cell().add(new Paragraph(text).setFont(font).setFontSize(10).setFontColor(DARK_COLOR))
                .setPadding(6).setTextAlignment(TextAlignment.CENTER)
                .setBackgroundColor(LIGHT_BG);
    }

    private void addBarChart(Document doc, List<CategoryScoreDTO> scores, PdfFont bold, PdfFont regular) {
        doc.add(new Paragraph("SCORE DISTRIBUTION")
                .setFont(bold).setFontSize(22).setFontColor(PRIMARY_COLOR)
                .setBorderBottom(new SolidBorder(PRIMARY_COLOR, 2)).setPaddingBottom(10));
        doc.add(new Paragraph("\n"));

        try {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for (CategoryScoreDTO score : scores) {
                dataset.addValue(score.getPercentage().doubleValue(), "Score %", score.getCategoryName());
            }

            JFreeChart chart = ChartFactory.createBarChart(
                    "Category-wise Score Distribution", "Category", "Score (%)",
                    dataset, PlotOrientation.VERTICAL, false, true, false);

            chart.setBackgroundPaint(java.awt.Color.WHITE);
            CategoryPlot plot = chart.getCategoryPlot();
            plot.setBackgroundPaint(java.awt.Color.WHITE);
            plot.setRangeGridlinePaint(java.awt.Color.LIGHT_GRAY);

            BarRenderer renderer = (BarRenderer) plot.getRenderer();
            java.awt.Color[] colors = {
                    new java.awt.Color(41, 98, 255),
                    new java.awt.Color(0, 184, 148),
                    new java.awt.Color(253, 121, 168),
                    new java.awt.Color(255, 159, 67),
                    new java.awt.Color(108, 92, 231)
            };
            for (int i = 0; i < scores.size(); i++) {
                renderer.setSeriesPaint(0, colors[i % colors.length]);
            }

            byte[] chartImage = chartToImage(chart, 500, 300);
            com.itextpdf.layout.element.Image img = new com.itextpdf.layout.element.Image(ImageDataFactory.create(chartImage));
            img.setWidth(UnitValue.createPercentValue(90));
            img.setHorizontalAlignment(HorizontalAlignment.CENTER);
            doc.add(img);
        } catch (Exception e) {
            doc.add(new Paragraph("Chart generation failed: " + e.getMessage())
                    .setFont(regular).setFontSize(10));
        }
    }

    private void addPieChart(Document doc, List<CategoryScoreDTO> scores, PdfFont bold, PdfFont regular) {
        doc.add(new Paragraph("SCORE BREAKDOWN")
                .setFont(bold).setFontSize(22).setFontColor(PRIMARY_COLOR)
                .setBorderBottom(new SolidBorder(PRIMARY_COLOR, 2)).setPaddingBottom(10));
        doc.add(new Paragraph("\n"));

        try {
            DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
            for (CategoryScoreDTO score : scores) {
                dataset.setValue(score.getCategoryName(), score.getPercentage().doubleValue());
            }

            JFreeChart chart = ChartFactory.createPieChart(
                    "Assessment Score Distribution", dataset, true, true, false);

            chart.setBackgroundPaint(java.awt.Color.WHITE);
            PiePlot<?> plot = (PiePlot<?>) chart.getPlot();
            plot.setBackgroundPaint(java.awt.Color.WHITE);
            plot.setOutlinePaint(null);
            plot.setShadowPaint(null);

            java.awt.Color[] colors = {
                    new java.awt.Color(41, 98, 255),
                    new java.awt.Color(0, 184, 148),
                    new java.awt.Color(253, 121, 168),
                    new java.awt.Color(255, 159, 67),
                    new java.awt.Color(108, 92, 231)
            };
            for (int i = 0; i < scores.size(); i++) {
                plot.setSectionPaint(scores.get(i).getCategoryName(), colors[i % colors.length]);
            }

            byte[] chartImage = chartToImage(chart, 450, 350);
            com.itextpdf.layout.element.Image img = new com.itextpdf.layout.element.Image(ImageDataFactory.create(chartImage));
            img.setWidth(UnitValue.createPercentValue(70));
            img.setHorizontalAlignment(HorizontalAlignment.CENTER);
            doc.add(img);
        } catch (Exception e) {
            doc.add(new Paragraph("Chart generation failed: " + e.getMessage())
                    .setFont(regular).setFontSize(10));
        }
    }

    private void addCategoryDetails(Document doc, List<CategoryScoreDTO> scores, PdfFont bold, PdfFont regular) {
        doc.add(new Paragraph("DETAILED CATEGORY ANALYSIS")
                .setFont(bold).setFontSize(22).setFontColor(PRIMARY_COLOR)
                .setBorderBottom(new SolidBorder(PRIMARY_COLOR, 2)).setPaddingBottom(10));
        doc.add(new Paragraph("\n"));

        DeviceRgb[] categoryColors = {PRIMARY_COLOR, SECONDARY_COLOR, ACCENT_COLOR,
                new DeviceRgb(255, 159, 67), new DeviceRgb(108, 92, 231)};

        for (int i = 0; i < scores.size(); i++) {
            CategoryScoreDTO score = scores.get(i);
            DeviceRgb color = categoryColors[i % categoryColors.length];

            doc.add(new Paragraph(score.getCategoryName().toUpperCase())
                    .setFont(bold).setFontSize(16).setFontColor(color)
                    .setMarginTop(15));

            Table detailTable = new Table(UnitValue.createPercentArray(new float[]{1, 1, 1}))
                    .setWidth(UnitValue.createPercentValue(100));

            detailTable.addCell(new Cell().add(
                    new Paragraph("Score: " + score.getRawScore() + "/" + score.getMaxScore())
                            .setFont(regular).setFontSize(11))
                    .setBackgroundColor(LIGHT_BG).setPadding(8).setBorder(Border.NO_BORDER));

            detailTable.addCell(new Cell().add(
                    new Paragraph("Percentage: " + score.getPercentage() + "%")
                            .setFont(bold).setFontSize(11).setFontColor(color))
                    .setBackgroundColor(LIGHT_BG).setPadding(8).setBorder(Border.NO_BORDER));

            String level = score.getPercentage().doubleValue() >= 75 ? "Strong" :
                    score.getPercentage().doubleValue() >= 50 ? "Moderate" : "Developing";
            detailTable.addCell(new Cell().add(
                    new Paragraph("Level: " + level)
                            .setFont(regular).setFontSize(11))
                    .setBackgroundColor(LIGHT_BG).setPadding(8).setBorder(Border.NO_BORDER));

            doc.add(detailTable);

            if (score.getTraitSummary() != null) {
                doc.add(new Paragraph(score.getTraitSummary())
                        .setFont(regular).setFontSize(10).setFontColor(DARK_COLOR)
                        .setMarginTop(5).setMarginBottom(10)
                        .setPaddingLeft(10).setBorderLeft(new SolidBorder(color, 3)));
            }
        }
    }

    private void addCareerRecommendations(Document doc, List<CareerRecommendationDTO> recommendations,
                                           PdfFont bold, PdfFont regular) {
        doc.add(new Paragraph("TOP CAREER RECOMMENDATIONS")
                .setFont(bold).setFontSize(22).setFontColor(PRIMARY_COLOR)
                .setBorderBottom(new SolidBorder(PRIMARY_COLOR, 2)).setPaddingBottom(10));
        doc.add(new Paragraph("\n"));

        for (CareerRecommendationDTO rec : recommendations) {
            Table card = new Table(UnitValue.createPercentArray(new float[]{1}))
                    .setWidth(UnitValue.createPercentValue(100))
                    .setMarginBottom(15);

            Cell headerCell = new Cell()
                    .add(new Paragraph("#" + rec.getRankOrder() + " " + rec.getCareerTitle())
                            .setFont(bold).setFontSize(14).setFontColor(WHITE))
                    .add(new Paragraph("Match: " + rec.getMatchPercentage() + "%")
                            .setFont(regular).setFontSize(11).setFontColor(WHITE))
                    .setBackgroundColor(PRIMARY_COLOR).setPadding(12).setBorder(Border.NO_BORDER);
            card.addCell(headerCell);

            Cell bodyCell = new Cell()
                    .add(new Paragraph(rec.getCareerDescription())
                            .setFont(regular).setFontSize(10).setFontColor(DARK_COLOR))
                    .add(new Paragraph("\n"))
                    .add(new Paragraph("Field: " + rec.getCareerField())
                            .setFont(regular).setFontSize(9).setFontColor(new DeviceRgb(100, 100, 100)))
                    .add(new Paragraph("Education: " + rec.getRequiredEducation())
                            .setFont(regular).setFontSize(9).setFontColor(new DeviceRgb(100, 100, 100)))
                    .add(new Paragraph("Salary Range: " + rec.getSalaryRange())
                            .setFont(regular).setFontSize(9).setFontColor(new DeviceRgb(100, 100, 100)))
                    .add(new Paragraph("Growth Outlook: " + rec.getGrowthOutlook())
                            .setFont(regular).setFontSize(9).setFontColor(new DeviceRgb(100, 100, 100)))
                    .setBackgroundColor(LIGHT_BG).setPadding(12).setBorder(Border.NO_BORDER);
            card.addCell(bodyCell);

            doc.add(card);
        }
    }

    private void addDevelopmentPlan(Document doc, AssessmentResultDTO result, PdfFont bold, PdfFont regular) {
        doc.add(new Paragraph("DEVELOPMENT PLAN & NEXT STEPS")
                .setFont(bold).setFontSize(22).setFontColor(PRIMARY_COLOR)
                .setBorderBottom(new SolidBorder(PRIMARY_COLOR, 2)).setPaddingBottom(10));
        doc.add(new Paragraph("\n"));

        doc.add(new Paragraph("Based on your assessment results, here are recommended next steps:")
                .setFont(regular).setFontSize(12).setFontColor(DARK_COLOR));
        doc.add(new Paragraph("\n"));

        String[] steps = {
                "1. Review your top career recommendations and research each option thoroughly.",
                "2. Identify the skills required for your preferred careers and create a learning plan.",
                "3. Seek mentorship from professionals in your areas of interest.",
                "4. Explore internships or virtual work experiences in your recommended fields.",
                "5. Consult with a career counsellor to finalize your career path.",
                "6. Choose the right academic stream, course, or college aligned with your career goals.",
                "7. Build a portfolio or gain certifications relevant to your chosen career.",
                "8. Work on strengthening areas where your scores indicate room for improvement."
        };

        for (String step : steps) {
            doc.add(new Paragraph(step)
                    .setFont(regular).setFontSize(11).setFontColor(DARK_COLOR)
                    .setMarginBottom(8).setPaddingLeft(10));
        }

        doc.add(new Paragraph("\n\n"));
        doc.add(new Paragraph("This report was generated by the Career Assessment Platform.")
                .setFont(regular).setFontSize(9).setFontColor(new DeviceRgb(150, 150, 150))
                .setTextAlignment(TextAlignment.CENTER));
        doc.add(new Paragraph("Powered by 5-Dimensional Psychometric Assessment Technology")
                .setFont(regular).setFontSize(9).setFontColor(new DeviceRgb(150, 150, 150))
                .setTextAlignment(TextAlignment.CENTER));
    }

    private byte[] chartToImage(JFreeChart chart, int width, int height) throws IOException {
        BufferedImage image = chart.createBufferedImage(width, height);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }
}
