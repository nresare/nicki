DESTDIR?=
PREFIX?=/usr
DATADIR?=$(PREFIX)/share/nicki
JARLIBDIR=$(DATADIR)/jars
SYSBINDIR?=$(PREFIX)/sbin
SYSCONFDIR?=/etc


dist/nicki-$(VERSION).jar:
	echo "Version is $(VERSION)"
	ant -lib /usr/share/java/ivy.jar -Dversion=$(VERSION)

# exclude groovy since we
install: jar_deps = $(filter-out %javadoc.jar %sources.jar \
                                 %groovy-all-1.7.6.jar, \
        $(wildcard lib/*.jar))
install: dist/nicki-$(VERSION).jar
	install -d $(DESTDIR)$(JARLIBDIR)
	install -m644 dist/nicki-$(VERSION).jar $(DESTDIR)$(JARLIBDIR)
	install -m644 $(jar_deps) $(DESTDIR)$(JARLIBDIR)
	install -d $(DESTDIR)$(DATADIR)/misc
	install -m644 misc/logback.xml $(DESTDIR)$(DATADIR)/misc
	install -d $(DESTDIR)$(SYSCONFDIR)
	install -m640 nicki.yaml $(DESTDIR)$(SYSCONFDIR)
	install -d $(DESTDIR)$(SYSBINDIR)
	install scripts/nicki $(DESTDIR)$(SYSBINDIR)


clean:
	ant clean
