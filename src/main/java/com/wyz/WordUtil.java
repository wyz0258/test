package com.wyz;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;

public class WordUtil {
	public static void main(String[] args) throws ParseException {
//		String fileName = "需求修改新增修复.docx";
//		String filePath = "C:" + File.separator + "Users" + File.separator + "l" + File.separator + "Desktop"
//				+ File.separator;
		String filePath=System.getProperty("user.dir"); 
		String modelPath = filePath + File.separator+ "test.docx";
		String destPath = filePath + File.separator+ "gggg.docx";
		Map<String, String> map = getMapData();
		List<FeeEntity> listData = getListData();
		wirteDoc(modelPath, destPath, map, listData);
	}

	public static void wirteDoc(String modelPath, String destPath, Map<String, String> map, List<FeeEntity> listData) {
		System.out.println(modelPath);
		File file = new File(modelPath);
		FileInputStream fis = null;
		OutputStream out = null;
		try {
			fis = new FileInputStream(file);
			XWPFDocument xwb = new XWPFDocument(fis);

			out = new FileOutputStream(destPath);
			/*
			 * 文本内容替换
			 */
			List<XWPFParagraph> list = xwb.getParagraphs();
			for (XWPFParagraph xp : list) {
				List<XWPFRun> runs = xp.getRuns();
				for (int i = 0; i < runs.size(); i++) {
					String oneparaString = runs.get(i).getText(runs.get(i).getTextPosition());
					for (Map.Entry<String, String> entry : map.entrySet()) {
						oneparaString = oneparaString.replace(entry.getKey(), entry.getValue());
					}
					runs.get(i).setText(oneparaString, 0);
				}

			}
			/*
			 * 表格1内容替换
			 */
			List<XWPFTable> tables = xwb.getTables();
			XWPFTable t1 = tables.get(0);
			List<XWPFTableRow> rows = t1.getRows();
			for (XWPFTableRow row : rows) {
				List<XWPFTableCell> tableCells = row.getTableCells();
				for (XWPFTableCell cell : tableCells) {
					String cellStr = cell.getText();
					for (Map.Entry<String, String> entry : map.entrySet()) {
						cellStr = cellStr.replace(entry.getKey(), entry.getValue());
					}
					cell.removeParagraph(0);
					cell.setText(cellStr);
					cell.setVerticalAlignment(XWPFVertAlign.CENTER);
				}
			}
			/*
			 * 表格2内容新增
			 */
			XWPFTable t2 = tables.get(1);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (FeeEntity et : listData) {
				XWPFTableRow createRow = t2.createRow();
				createRow.getCell(0).setText(et.getQishu());
				createRow.getCell(0).setVerticalAlignment(XWPFVertAlign.CENTER);
				createRow.getCell(1).setText(sdf.format(et.getHkrq()));
				createRow.getCell(1).setVerticalAlignment(XWPFVertAlign.CENTER);
				createRow.getCell(2).setText(et.getHkje().toString());
				createRow.getCell(2).setVerticalAlignment(XWPFVertAlign.CENTER);
				createRow.getCell(3).setText(et.getRemark());
				createRow.getCell(3).setVerticalAlignment(XWPFVertAlign.CENTER);
				// System.out.println(et.getQishu());
				// System.out.println(et.getHkje());
				// System.out.println(sdf.format(et.getHkrq()));
				// System.out.println(et.getRemark());
			}
			xwb.write(out);
			fis.close();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Map<String, String> getMapData() {
		Map<String, String> map = new HashMap<>();
		map.put("barCode", "111111111111");
		map.put("contract", "22222222222");
		map.put("amount", "10000");
		map.put("lx", "10%(年利率) / 12 个月");
		map.put("dkzq", "12月");
		map.put("total", "15600");
		map.put("fee", "4600");
		return map;
	}

	public static List<FeeEntity> getListData() throws ParseException {
		List<FeeEntity> list = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 1; i < 13; i++) {
			FeeEntity et = new FeeEntity();
			et.setHkje(new BigDecimal(1300));
			et.setQishu("第" + i + "期");
			et.setHkrq(sdf.parse("2016-" + i + "-05"));
			et.setRemark("还款");
			list.add(et);
		}
		return list;
	}
}
