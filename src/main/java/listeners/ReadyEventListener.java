package listeners;

import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class ReadyEventListener extends ListenerAdapter {

	public ReadyEventListener() {

	}

	@Override
	public void onReady(@NotNull ReadyEvent event) {
		System.out.println("YESYESYESYES");
		event.getJDA().updateCommands().addCommands().queue();
	}
}
