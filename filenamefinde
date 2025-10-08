import os, re

output_file = "assignment_topics.txt"
pattern = re.compile(r'Agenda\s*:\s*(.+)', re.IGNORECASE)

with open(output_file, "w", encoding="utf-8") as out:
    for filename in sorted(os.listdir(".")):
        if filename.endswith(".Rmd") or filename.endswith(".html"):
            try:
                with open(filename, encoding="utf-8", errors="ignore") as f:
                    content = f.read()
                match = pattern.search(content)
                if match:
                    topic = match.group(1).strip()
                else:
                    topic = "(no agenda line found)"
                out.write(f"{filename} — {topic}\n")
            except Exception as e:
                out.write(f"{filename} — error reading file ({e})\n")

print(f"Summary written to {output_file}")
