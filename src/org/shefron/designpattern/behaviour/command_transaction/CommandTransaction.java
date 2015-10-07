package org.shefron.designpattern.behaviour.command_transaction;

import java.util.concurrent.LinkedBlockingQueue;

public class CommandTransaction {

	private static LinkedBlockingQueue<Command> queue = new LinkedBlockingQueue<Command>();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Document doc = new Document();
		doc.setTitle("ÄãºÃÂð");
		doc.setAuthor("shefron");
		doc.setContent("some content");
		doc.setDate("2012-12-12 00:00:00");

		System.out.println("the old doc author :" + doc.getAuthor()
				+ ",title :" + doc.getTitle());

		PasteCommand paste = new PasteCommand(doc);
		paste.execute();
		WriteCommand write = new WriteCommand(doc);
		write.execute();
		queue.add(paste);
		queue.add(write);

		System.out.println("after execute doc author :" + doc.getAuthor()
				+ ",title :" + doc.getTitle());
		while (queue.peek() != null) {
			Command command = (Command) queue.poll();
			command.undo();
		}
		// »Ö¸´
		// for (int i=0;i<queue.size();i++){
		// Command command = (Command)queue.poll();
		// command.undo();
		// }
		System.out.println("after undo doc author :" + doc.getAuthor()
				+ ",title :" + doc.getTitle());
	}

}
