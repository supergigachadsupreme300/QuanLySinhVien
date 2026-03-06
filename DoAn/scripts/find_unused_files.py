#!/usr/bin/env python3
import os
import re

root = os.path.dirname(os.path.dirname(__file__))

java_files = []
for dirpath, dirs, files in os.walk(root):
    for f in files:
        if f.endswith('.java'):
            java_files.append(os.path.join(dirpath, f))

def extract_types(path):
    types = set()
    try:
        with open(path, 'r', encoding='utf-8') as fh:
            data = fh.read()
    except Exception:
        return types
    for m in re.finditer(r"\b(class|interface|enum)\s+(\w+)", data):
        types.add(m.group(2))
    return types

all_types = {}
for jf in java_files:
    names = extract_types(jf)
    if not names:
        # fallback: use filename (without .java)
        names = {os.path.splitext(os.path.basename(jf))[0]}
    all_types[jf] = names

# read all files content
contents = {}
for jf in java_files:
    try:
        with open(jf, 'r', encoding='utf-8') as fh:
            contents[jf] = fh.read()
    except Exception:
        contents[jf] = ''

unused = []
for jf, types in all_types.items():
    used = False
    for t in types:
        pattern = re.compile(r"\b" + re.escape(t) + r"\b")
        for other, text in contents.items():
            if other == jf: continue
            if pattern.search(text):
                used = True
                break
        if used: break
    if not used:
        unused.append(jf)

print("Found {} Java files; {} appear unused (no references from other files):".format(len(java_files), len(unused)))
for u in sorted(unused):
    print(u)
