default: bake

bake:
	mkdir -p build
	nxjc -d build src/Sonaris.java	

upload:
	cd build && sudo nxj -r Sonaris

go: bake upload
