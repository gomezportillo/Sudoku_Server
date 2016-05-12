#SOLVER - http://www.thonky.com/sudoku/solution-count

class SudokuGenerator:

    def __init__(self, file):
        self.file = file
        self.counter = 1

    def generate(self):
        with open(self.file) as f:
            lines = f.readlines()
            sud_lines = ""

            for line in lines:
                if line[0] != 'G':
                    sud_lines += line.replace("\r\n", "")
                else:
                    filename = "generated/sudoku{}.txt".format(self.counter)
                    with open(filename, 'w+') as f2:
                        f2.write(sud_lines+"\r\n")
                    sud_lines = ""
                    self.counter += 1

generator = SudokuGenerator("sudokus.txt")
generator.generate()
