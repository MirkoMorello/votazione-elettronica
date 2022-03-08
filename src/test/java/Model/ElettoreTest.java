package Model;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ElettoreTest {


	@Test
	public void testElettore1() throws Exception {
		Elettore e = new Elettore("MRTNNA97C07F205Z", "anna", "Mariotti", LocalDate.parse("1997-03-07"), "Milano", "Italia", 'f');
		assertNotNull(e);
	}
	
	@Test
	public void testElettore2() throws Exception {
		Elettore e = new Elettore("DTRFZA94H02H501Z", "Fazio", "Auditore", LocalDate.parse("1994-06-02"), "Roma", "Italia", 'm');
		assertNotNull(e);
	}
	
	@Test(expected = java.lang.Exception.class)
	public void testWrongElettore1() throws Exception {
		Elettore e = new Elettore("MRRNNA97C07F205Z", "anna", "Mariotti", LocalDate.parse("1997-03-07"), "Milano", "Italia", 'm');
	}
	
	@Test(expected = java.lang.Exception.class)
	public void testWrongElettor2() throws Exception {
		Elettore e = new Elettore("FTRFZA94H02H501Z", "Fazio", "Auditore", LocalDate.parse("1994-06-02"), "Roma", "Italia", 'm');
	}
	
	@Test(expected = java.lang.Exception.class)
	public void testSetCF() throws Exception {
		Elettore e = new Elettore("MRTNNA97C07F205Z", "anna", "Mariotti", LocalDate.parse("1997-03-07"), "Milano", "Italia", 'm');
		assertNotNull(e);
		e.setCF("MRRNNA97C07F205Z");
	}

	@Test
	public void testCanVote1() throws Exception {
		Elettore e = new Elettore("MTTFNC08M01F839B", "Francesca", "Mattarella", LocalDate.parse("2008-08-01"), "Napoli", "Italia", 'f');
		assertFalse(e.canVote());
	}
	
	@Test
	public void testCanVote2() throws Exception {
		Elettore e = new Elettore("MRLNTN24E13A462V", "ANTONIO", "MARIELLO", LocalDate.parse("1924-05-13"), "Ascoli Piceno", "Italia", 'm');
		assertTrue(e.canVote());
	}
}
