package com.ule.uhj.util;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfUtils {

	private static Logger log = LoggerFactory.getLogger(PdfUtils.class);

	public static byte[] createPdfFileStream1(String title, Map<String, Object> mapBody) {

		try {
			log.info("uploadPdfFile create pdf start...");
			Rectangle rect = new Rectangle(PageSize.B4.rotate());
			Document doc = new Document(rect);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(doc, os);
			doc.open();
			BaseFont bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			Font font = new Font(bfChinese, 12, Font.NORMAL);

			writer.setPdfVersion(PdfWriter.PDF_VERSION_1_2);
			doc.addTitle("接口数据文件");
			doc.addAuthor("uhjSystem");
			doc.addCreator("uhjSystem");
			doc.open();
			/* map做成表格 */
			if (mapBody != null && mapBody.size() > 0) {
				List<String> header = (List<String>) mapBody.get("header");
				List<Float> sizeRatio = (List<Float>) mapBody.get("sizeRatio");
				int tableSize = header.size();
				PdfPTable table = new PdfPTable(tableSize);
				table.setSplitRows(false);
				table.setWidthPercentage(100);
				table.setTotalWidth(1000);
				float[] floatSize = new float[header.size()];
				for (int i = 0; i < sizeRatio.size(); i++) {
					Float ratio = sizeRatio.get(i);
					floatSize[i] = 1000 * ratio / 10;
				}
				table.setTotalWidth(floatSize);
				table.setLockedWidth(true);
				table.setSpacingBefore(10f);
				table.setSpacingAfter(10f);
				table.getDefaultCell().setBorder(1);
				for (String key : header) {
					PdfPCell headerCell = new PdfPCell(new Paragraph(key, font));
					headerCell.setBorderColor(BaseColor.BLACK);
					headerCell.setBackgroundColor(BaseColor.WHITE);
					headerCell.setRowspan(2);
					headerCell.setPaddingLeft(10);
					headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
					headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(headerCell);
				}
				List<List<String>> body = (List<List<String>>) mapBody.get("body");
				for (List<String> oneBody : body) {
					for (String oneCell : oneBody) {
						PdfPCell bodyCell = new PdfPCell(new Paragraph(oneCell, font));
						bodyCell.setBorderColor(BaseColor.BLACK);
						bodyCell.setBackgroundColor(BaseColor.WHITE);
						bodyCell.setRowspan(2);
						bodyCell.setPaddingLeft(10);
						bodyCell.setFixedHeight(500);
						bodyCell.setHorizontalAlignment(Element.ALIGN_LEFT);
						bodyCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(bodyCell);
					}
				}
				doc.add(table);
			}
			doc.close();
			return os.toByteArray();
		} catch (Exception ex) {
			log.error("uploadPdfFile error", ex);
		}
		return null;
	}

	public static byte[] createPdfFileStream(String title, Map<String, Object> mapBody) {

		try {
			log.info("uploadPdfFile create pdf start...");
			Rectangle rect = new Rectangle(PageSize.A4.rotate());
			Document doc = new Document(rect);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(doc, os);
			doc.open();
			BaseFont bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			Font font = new Font(bfChinese, 12, Font.NORMAL);

			writer.setPdfVersion(PdfWriter.PDF_VERSION_1_2);
			doc.addTitle("接口数据文件");
			doc.addAuthor("uhjSystem");
			doc.addCreator("uhjSystem");
			doc.open();
			/* map做成表格 */
			if (mapBody != null && mapBody.size() > 0) {
				List<String> header = (List<String>) mapBody.get("header");
				List<Float> sizeRatio = (List<Float>) mapBody.get("sizeRatio");
				List<String> body = (List<String>) mapBody.get("body");
				PdfPTable table = new PdfPTable(2);
				table.setSplitRows(true);
				table.setSplitLate(false);
				table.setWidthPercentage(100);
				table.setTotalWidth(800);
				float[] floatSize = new float[2];
				for (int i = 0; i < sizeRatio.size(); i++) {
					Float ratio = sizeRatio.get(i);
					floatSize[i] = 800 * ratio / 10;
				}
				table.setTotalWidth(floatSize);
				table.setLockedWidth(true);
				table.setSpacingBefore(10f);
				table.setSpacingAfter(10f);
				table.getDefaultCell().setBorder(1);
				for(int i=0;i<header.size();i++) {
					PdfPCell headerCell = new PdfPCell(new Paragraph(header.get(i), font));
					headerCell.setBorderColor(BaseColor.BLACK);
					headerCell.setBackgroundColor(BaseColor.WHITE);
					headerCell.setRowspan(2);
					headerCell.setPaddingLeft(10);
					headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
					headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(headerCell);
					String bodyStr = body.get(i);
					PdfPCell bodyCell = new PdfPCell(new Paragraph(bodyStr, font));
					bodyCell.setBorderColor(BaseColor.BLACK);
					bodyCell.setBackgroundColor(BaseColor.WHITE);
					bodyCell.setRowspan(2);
					bodyCell.setPaddingLeft(10);
					bodyCell.setHorizontalAlignment(Element.ALIGN_LEFT);
					bodyCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(bodyCell);
				}
				doc.add(table);
			}
			doc.close();
			return os.toByteArray();
		} catch (Exception ex) {
			log.error("createPdfFileStream error", ex);
		}
		return null;
	}

}