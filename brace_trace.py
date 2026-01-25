import sys

def check_braces(file_path):
    stack = []
    try:
        with open(file_path, 'r', encoding='utf-8') as f:
            lines = f.readlines()
    except Exception as e:
        print(f"Error reading file: {e}")
        return

    in_block_comment = False
    
    for i, line in enumerate(lines):
        line_num = i + 1
        j = 0
        while j < len(line):
            if not in_block_comment and line[j:j+2] == '/*':
                in_block_comment = True
                j += 2
                continue
            if in_block_comment:
                if line[j:j+2] == '*/':
                    in_block_comment = False
                    j += 2
                else:
                    j += 1
                continue
            
            if line[j:j+2] == '//':
                break
            
            c = line[j]
            if c == '{':
                stack.append(line_num)
            elif c == '}':
                if stack:
                    stack.pop()
                else:
                    print(f"Error: Unexpected closing brace at line {line_num}")
            j += 1

    if stack:
        print("Unclosed braces stack (Line numbers):")
        for l in stack:
            print(l)
    else:
        print("All braces balanced.")

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Usage: python brace_trace.py <file>")
    else:
        check_braces(sys.argv[1])
