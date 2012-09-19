package com.resare.nicki;

import org.junit.Test;
import org.junit.Assert;

import java.util.Map;


/**
 * Tests JSONTools
 */
public class JSONToolsTest {

  @Test
  public void testParseJson() {
    String s = "{'severity':'AVERAGE', 'id': '277880834@server1', "
               + "'message': 'Webservice nginx errors high on client1', "
               + "'entity':'client1', "
               + "'type': 'OK', 'detail': 'Some additional details',"
               + "'extra_fields': {'fai classes': 'WS'}}";
    Alert parsed = JSONTools.parseAlert(s);
    Assert.assertNotNull(parsed);
    Assert.assertEquals(Severity.AVERAGE, parsed.getSeverity());
    Assert.assertEquals("Webservice nginx errors high on client1",
                        parsed.getMessage());
    Assert.assertEquals("client1", parsed.getEntity());
    Assert.assertEquals("Some additional details", parsed.getDetail());
    Assert.assertEquals("277880834@server1", parsed.getId());
    Assert.assertEquals(AlertType.OK, parsed.getType());
    Map<String,String> extra = parsed.getExtraFields();
    Assert.assertEquals("WS", extra.get("fai classes"));
  }

  @Test
  public void testParseFromZabbix() {
    String s = "{\n"
               + "  \"status\": \"OK\", \n"
               + "  \"severity\": \"Information\", \n"
               + "  \"extra_fields\": {\n"
               + "    \"Puppet classes\": \"hostbase settings stdlib stdlib::stages "
               + "hostbase hostbase::bootstrap hostbase::bootstrap::mail apt apt::params "
               + "apt::update concat::setup dumpster::client firewall::config::ssh_servicenet "
               + "firewall firewall::install firewall::config hostbase::adminusers "
               + "hostbase::basefacts hostbase::blacklist hostbase::certs "
               + "hostbase::custom_kernel hostbase::kerberos hostbase::kernel_modules "
               + "hostbase::mail hostbase::manage_startup_scripts hostbase::manage_users "
               + "hostbase::netconsole hostbase::openssh hostbase::pam_config "
               + "hostbase::puppetd hostbase::reset_lom hostbase::rundeck hostbase::screen "
               + "hostbase::security_upgrade hostbase::shell hostbase::ssh hostbase::subversion "
               + "hostbase::sysctl hostbase::syslog_ng hostbase::bofh unbound munin "
               + "resolvconf zabbix ntp\", \n"
               + "    \"Event time\": \"2012.09.19 08:58:54\", \n"
               + "    \"IP1\": \"193.182.12.24\", \n"
               + "    \"URL\": \"\", \n"
               + "    \"Fai classes\": \"DEFAULT LINUX AMD64 FAI_SOFTUPDATE FAIBASE SPOTIFY SGI HARDWARE MEGARAIDSAS ASHBURN STABLE SERVICE PLAYLIST4_TEST PLAYLIST_MIGRATOR PRODUCTION alexis.ash.spotify.net LAST\"\n"
               + "  }, \n"
               + "  \"detail\": \"2012.09.19 08:55:11\", \n"
               + "  \"entity\": \"alexis.ash.spotify.net\", \n"
               + "  \"message\": \"puppet configuration isn't applied on alexis.ash.spotify.net\", \n"
               + "  \"id\": \"295301097\"\n"
               + "}";

    Alert parsed = JSONTools.parseAlert(s);
    Assert.assertNotNull(parsed);
  }

}
