package sh.odb.plugin.viewstatic;

import java.io.BufferedWriter;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Locale;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class ODBFreemarkerView extends FreeMarkerView {

	public final static String STATIC_PAGE = "STATIC_PAGE";

	@Override
	protected void doRender(Map model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		exposeModelAsRequestAttributes(model, request);
		SimpleHash fmModel = buildTemplateModel(model, request, response);
		Locale locale = RequestContextUtils.getLocale(request);
		/*
		 * 默认不生成静态文件,除非在Action中进行如下设置 model.addAttribute("STATIC_PAGE", true);
		 */
		if (model.get(STATIC_PAGE) == null || Boolean.FALSE.equals(model.get(STATIC_PAGE))) {
			processTemplate(getTemplate(locale), fmModel, response);
		} else {
			createHTML(getTemplate(locale), fmModel, request, response);
		}

	}

	private void createHTML(Template template, SimpleHash model, HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateException, ServletException {
		String filepath = ((SimpleHash) model.get("root")).get("StaticFilePath").toString();
		CharArrayWriter charArrayWriter = new CharArrayWriter();
		template.process(model, charArrayWriter);
		charArrayWriter.flush();
		creatFile(filepath, charArrayWriter);
		charArrayWriter.writeTo(response.getWriter());
		charArrayWriter.close();

	}

	private void creatFile(String filePath, CharArrayWriter writer) throws IOException {
		File file = new File(filePath);
		File dir = file.getParentFile();
		if (!dir.exists()) {
			dir.mkdirs();
		}
		BufferedWriter os = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8"));
		writer.writeTo(os);
		os.flush();
		os.close();
	}

}