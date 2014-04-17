package com.joker.dict.service;

import java.io.File;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.joker.dict.model.Noun;
import com.joker.dict.model.Word;

@RunWith(Arquillian.class)
public class DictServiceTest {
	@Inject
	private DictService service;
	
    @Deployment
    public static WebArchive createTestArchive() {
    	File[] libs = Maven.resolver()
                .loadPomFromFile("pom.xml").resolve("org.jsoup:jsoup")
                .withTransitivity().as(File.class);
    	
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackages(true, DictService.class.getPackage().getName(), Word.class.getPackage().getName())
                .addAsLibraries(libs)
                .addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
    }
	
	@Test
	public void fetchDictDesc() throws Exception {
		Noun noun = service.getDictDescription("Pech");
		Assert.assertNotNull(noun);
		Assert.assertEquals("das", noun.getGender());
	}
}
