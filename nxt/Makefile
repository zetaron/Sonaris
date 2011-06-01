default: bake

bake:
	mkdir -p build
	nxjc -d build src/*.java	

upload:
	cd build && sudo nxj -u -r Sonaris

go: bake upload
