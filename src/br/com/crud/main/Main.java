package br.com.crud.main;

import java.sql.SQLException;

import br.com.crud.ui.PessoaForm;
import totalcross.sys.Settings;
import totalcross.ui.MainWindow;
import totalcross.ui.gfx.Color;

public class Main extends MainWindow {

	public Main() throws SQLException {
		super("Cadastro de Pessoas", VERTICAL_GRADIENT);
		gradientTitleStartColor = 0;
		gradientTitleEndColor = 0xAAAAFF;

		setUIStyle(Settings.Vista);
		// Use font height for adjustments, not pixels
		Settings.uiAdjustmentsBasedOnFontHeight = true;
		setBackColor(Color.brighter(Color.BRIGHT, Color.LESS_STEP));
		swap(new PessoaForm());
	}
}
