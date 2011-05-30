default: bake

clean:
	rm -r build

bake:
	mkdir -p build
	cd build && cmake .. && make -j3

run:
	cd bin/ && ./sonaris
