package br.com.tecsinapse.glimpse.server.protocol;

import org.testng.Assert;
import org.testng.annotations.Test;

public class StartOpTest {
	
  @Test
  public void parse() {
	  String script = "myscript";
	  String xml = String.format("<start><script>%s</script></start>", script);
	  
	  StartOp startOp = StartOp.parse(xml);
	  Assert.assertEquals(startOp.getScript(), script);
  }
  
}
