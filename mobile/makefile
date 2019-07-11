SOURCE=libs
BUILD=build
DOCS=docs
JARS=libs/json.jar;libs/json-simple-1.1.jar
PATHS="$(BUILD);$(JARS)"
MED_PATH=$(SOURCE)/br/uerj/dicafe/medicamentos
UTILS_PATH=$(SOURCE)/br/uerj/dicafe/utils
INFO_PATH=$(SOURCE)/br/uerj/dicafe/info

all: utils info medicamentos demo

.PHONY: run
run:
	java -cp "$(BUILD);libs/json.jar" Demo

.PHONY: medicamentos
medicamentos: $(MED_PATH)/ListaMedicamentos.java $(MED_PATH)/Medicamentos.java $(MED_PATH)/Medicamento.java $(MED_PATH)/MedicamentosAPI.java
	javac -cp $(PATHS) -d $(BUILD) $(MED_PATH)/*.java

.PHONY: utils
utils: $(UTILS_PATH)/JSONUtils.java
	javac -cp $(PATHS) -d $(BUILD) $(UTILS_PATH)/*.java

.PHONY: info
info: $(INFO_PATH)/Exames.java $(INFO_PATH)/Intercorrencias.java
	javac -cp $(PATHS) -d $(BUILD) $(INFO_PATH)/*.java

.PHONY: demo
demo: $(SOURCE)/Demo.java
	javac -cp $(PATHS) -d $(BUILD) $(SOURCE)/Demo.java

.PHONY: docs
docs:
	javadoc -cp $(PATHS) -sourcepath $(SOURCE) -d $(DOCS) -subpackages . -noqualifier java.lang

.PHONY: clean
clean:
	rm $(BUILD)/* -r

.PHONY: clean-docs
clean-docs:
	rm $(DOCS)/* -r

