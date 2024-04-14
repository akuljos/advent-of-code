f_out = open("snowverload_reformatted_test.txt", "w")

with open("snowverload_test.txt") as f_in:
    line = f_in.readline()[:-1].split(" ")

    while line != ['']:
        start_node = line[0][:-1]
        for i in range(1,len(line)):
            f_out.write(start_node + " " + line[i] + "\n")

        line = f_in.readline()[:-1].split(" ")
