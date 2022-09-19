package global;

import shapes.TShape;
import shapes.TPointedSquare;
import shapes.TTriangle;
import transformers.Drawer;
import transformers.Mover;
import transformers.Resizer;
import transformers.Rotator;
import transformers.Transformer;
import shapes.TRectangle;
import shapes.TRombus;
import shapes.TSelection;
import shapes.TOval;
import shapes.TLine;
import shapes.TPolygon;

public class Constants {
	
	public enum ETransformationStyle {
		e2Point, 
		eNPoint
	}
	public enum ETools { 
		eSelection("����", new TSelection(), "data/����.png", ETransformationStyle.e2Point),
		eRectangle("�׸�", new TRectangle(), "data/�׸�.png", ETransformationStyle.e2Point),
		eOval("���׶��", new TOval(), "data/��.png", ETransformationStyle.e2Point),
		eTriangle("�ﰢ��", new TTriangle(), "data/�ﰢ��.png", ETransformationStyle.e2Point),
		eLine("����", new TLine(), "data/����.png", ETransformationStyle.e2Point),
		eStar("����Ŭ�ι�", new TPointedSquare(), "data/����Ŭ�ι�.png", ETransformationStyle.e2Point),
		eRombus("������", new TRombus(), "data/������.png", ETransformationStyle.e2Point),
		ePolygon("�ٰ���", new TPolygon(), "data/�ٰ���.png", ETransformationStyle.eNPoint);
		private String label;
		private TShape tool;
		private String path;
		private ETransformationStyle eTransformationStyle;
		
		private ETools(String label, TShape tool, String path, ETransformationStyle eTransformationStyle) {
			this.label = label;
			this.tool = tool;
			this.path = path;
			this.eTransformationStyle = eTransformationStyle;
		}
		public String getLabel() {
			return this.label;
		}
		public TShape newShape() {
			return this.tool.clone();
		}
		public String getPath() {
			return this.path;
		}
		public ETransformationStyle getTransformationStyle() {
			return this.eTransformationStyle;
		}
	}
	public enum EFileMenuSave {
		eisUpdatedAsk("���� ������ �����Ͻðڽ��ϱ�?"),
		eisUpdatedCheck("������ Ȯ���մϴ�."),
		eopenFile("���� ����"),
		esaveFile("���� ����"),
		ecurrentDirectory("./");

		private String title;		
		private EFileMenuSave(String title) {
			this.title = title;
		}		
		public String getTitle() {
			return this.title;
		}
	}
	public enum ETransformers {
		eDrawer(new Drawer()),
		eMover(new Mover()),
		eResizer(new Resizer()),
		eRotator(new Rotator());
		
		private Transformer transformer;
		private ETransformers(Transformer transformer) {
			this.transformer = transformer;			
		}
		public Transformer getTransformer() {
			return this.transformer;
		}	
	}
	
	public enum EFileMenu {
		eNew("���� �����"),
		eOpen("����"),
		eClose("�ݱ�"),
		eSave("����"),
		eSaveAs("�ٸ��̸�����"),
		ePrint("����Ʈ"),
		eQuit("����");
		
		private String label;
		
		private EFileMenu(String label) {
			this.label = label;
		}
		public String getLabel() {
			return this.label;
		}
	}
}
