ARCHIVE = tema.zip
README = README.md

GIT = $(shell find .git -type f -print)
SRC = $(shell find src -type f -name "*.java" -print)

all: $(ARCHIVE)

clean:
	-rm -f $(ARCHIVE)

$(ARCHIVE): $(README) $(GIT) $(SRC)
	zip -FSr $@ $^

