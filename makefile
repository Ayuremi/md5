encode: md5.class 
	@java md5 encode ${ARGS}

decode: md5.class
	@java md5 decode ${ARGS}

test: md5.class
	@java md5 test ${ARGS}

md5.class: md5.java
	@javac md5.java

