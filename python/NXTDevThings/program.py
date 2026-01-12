import multiprocessing

if __name__ == "__main__":
	music = multiprocessing.Process(target=exec(open("data.py").read()))
	robot = multiprocessing.Process(target=exec(open("sort.py").read()))
	
	

	music.start()	
	robot.start()
	
	
