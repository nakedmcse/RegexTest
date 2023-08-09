import random
import re
import time

inputs = [''] * 100000
outputs_regex = [''] * 100000
outputs_split = [''] * 100000

# Compiled regexes
PATTERN_END = re.compile(r"&option=one$")
PATTERN_START_MIDDLE = re.compile(r"option=one&")

def generate_random_text(length):
    characters = "abcdefghijklmnopqrstuvwxyz"
    return ''.join(random.choice(characters) for _ in range(length))

def generate_options_string():
    options = ["option=one"]
    for _ in range(1, 10):
        random_text = generate_random_text(5)
        options.append(f"{random_text}=100")
    random.shuffle(options)
    return '&'.join(options)

def fill_inputs():
    for i in range(100000):
        inputs[i] = generate_options_string()

def test_split_join():
    for i in range(100000):
        parts = inputs[i].split("&")
        parts = [part for part in parts if "option" not in part]
        outputs_split[i] = '&'.join(parts)

def test_regex():
    for i in range(100000):
        outputs_regex[i] = re.sub(r"option=one$", "", inputs[i])
        outputs_regex[i] = re.sub(r"option=one&", "", outputs_regex[i])

def test_compiled_regex():
    for i in range(100000):
        intermediate_result = PATTERN_END.sub('', inputs[i])
        outputs_regex[i] = PATTERN_START_MIDDLE.sub('', intermediate_result)

if __name__ == '__main__':
    print("Building Test Inputs")
    fill_inputs()
    print()

    print("Testing Split/Join method")
    start_split_join_time = time.time()
    test_split_join()
    end_split_join_time = time.time()
    print(f"Execution time {(end_split_join_time - start_split_join_time) * 1000} ms")
    print()

    print("Testing Regex method")
    start_regex_time = time.time()
    test_regex()
    end_regex_time = time.time()
    print(f"Execution time {(end_regex_time - start_regex_time) * 1000} ms")
    print()

    print("Testing Compiled Regex method")
    start_compiled_regex_time = time.time()
    test_compiled_regex()
    end_compiled_regex_time = time.time()
    print(f"Execution time {(end_compiled_regex_time - start_compiled_regex_time) * 1000} ms")
    print()
