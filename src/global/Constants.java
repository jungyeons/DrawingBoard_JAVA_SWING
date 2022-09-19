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
		eSelection("선택", new TSelection(), "data/선택.png", ETransformationStyle.e2Point),
		eRectangle("네모", new TRectangle(), "data/네모.png", ETransformationStyle.e2Point),
		eOval("동그라미", new TOval(), "data/원.png", ETransformationStyle.e2Point),
		eTriangle("삼각형", new TTriangle(), "data/삼각형.png", ETransformationStyle.e2Point),
		eLine("라인", new TLine(), "data/직선.png", ETransformationStyle.e2Point),
		eStar("뾰족클로버", new TPointedSquare(), "data/뾰족클로버.png", ETransformationStyle.e2Point),
		eRombus("마름모", new TRombus(), "data/마름모.png", ETransformationStyle.e2Point),
		ePolygon("다각형", new TPolygon(), "data/다각형.png", ETransformationStyle.eNPoint);
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
		eisUpdatedAsk("변경 내용을 저장하시겠습니까?"),
		eisUpdatedCheck("변경을 확인합니다."),
		eopenFile("파일 열기"),
		esaveFile("파일 저장"),
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
		eNew("새로 만들기"),
		eOpen("열기"),
		eClose("닫기"),
		eSave("저장"),
		eSaveAs("다른이름으로"),
		ePrint("프린트"),
		eQuit("종료");
		
		private String label;
		
		private EFileMenu(String label) {
			this.label = label;
		}
		public String getLabel() {
			return this.label;
		}
	}
}
